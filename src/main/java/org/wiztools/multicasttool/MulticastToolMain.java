package org.wiztools.multicasttool;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 *
 * @author subwiz
 */
public class MulticastToolMain {
    
    private static final int EXIT_CODE_CLI_PARSE_ERROR = 1;
    
    private static void printHelp(PrintStream out) {
        out.println("Usage: java -jar multicast-tool-VERSION-jar-with-dependencies.jar [-l | -s] <address> <port>");
        out.println("Where:");
        out.println("\t-l\tJoin the Multicast group and listen for data");
        out.println("\t-s\tOpen the send interface");
        out.println("To print this message:");
        out.println("\t-h\tHelp message");
    }
    
    public static void main(String[] arg) {
        OptionParser parser = new OptionParser("lhs");
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
        
        try{
            final InetAddress address = InetAddress.getByName(params.get(0));
            final int port = Integer.parseInt(params.get(1));
            
            if(options.has("l")) {
                MulticastSnifferThread t = new MulticastSnifferThread(address, port);
                new Thread(t).start();
                new Thread(new CliControlThread(t)).start();
            }
            else if(options.has("s")) {
                new Thread(
                        new MulticastSendThread(
                                address,
                                port,
                                new ConsoleDataCollector())).start();
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
