package org.wiztools.multicasttool;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * The program entry class with public static void main(...) method.
 * @author subwiz
 */
public class MulticastToolMain {
    
    private static final int EXIT_CODE_CLI_PARSE_ERROR = 1;
    
    private static void printHelp(PrintStream out) {
        out.println("Usage: java -jar multicast-tool-VERSION-jar-with-dependencies.jar (-l | -s) [OPTION] <address> <port>");
        out.println("Where:");
        out.println("\t-l\tJoin the Multicast group and listen for data.");
        out.println("\t-s\tOpen the send interface.");
        out.println("Options:");
        out.println("\t-v\tVerbose output. Additional info will be output to STDERR.");
        out.println("\t-i\tInteractive input. Used when -s is specified, ignored other times.");
        out.println("\t-h\tPrint this message and quit.");
        out.println("Data is read and written to the STDIN and STDOUT respectively.");
        out.println("Quick reference:");
        out.println("\tMulticast IP range: 224.0.0.0 to 239.255.255.255");
    }
    
    public static void main(String[] arg) {
        OptionParser parser = new OptionParser("lhsiv");
        OptionSet options = parser.parse(arg);
        
        if(options.has("h")) {
            printHelp(System.out);
            return;
        }
        
        final List<String> params = options.nonOptionArguments();
        
        if(params.size() != 2) {
            System.err.println("There has to be one option and two arguments:");
            printHelp(System.err);
            System.exit(EXIT_CODE_CLI_PARSE_ERROR);
        }
        
        if(!(options.has("l") || options.has("s"))) {
            System.err.println("Either option -l or -s needs to be present:");
            printHelp(System.err);
            System.exit(EXIT_CODE_CLI_PARSE_ERROR);
        }
        
        // Options:
        RuntimeOptionsImpl rtOpts = new RuntimeOptionsImpl();
        
        if(options.has("v")) {
            rtOpts.setVerbose(true);
        }
        
        try{
            final InetAddress address = InetAddress.getByName(params.get(0));
            final int port = Integer.parseInt(params.get(1));
            
            if(options.has("l")) {
                MulticastSnifferThread t = new MulticastSnifferThread(address, port, rtOpts);
                new Thread(t).start();
                new Thread(new CliControlThread(t)).start();
            }
            else if(options.has("s")) {
                final DataCollector dataCollector = options.has("i")?
                        new ConsoleInteractiveDataCollector():
                        new ConsoleDataCollector();

                new Thread(
                        new MulticastSendThread(
                                address,
                                port,
                                dataCollector)).start();
            }
        }
        catch(UnknownHostException ex) {
            ex.printStackTrace(System.err);
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
