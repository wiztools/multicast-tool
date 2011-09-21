package org.wiztools.multicasttool;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author subwiz
 */
public class CliControlThread implements Runnable {
    
    // List<String> quitCommands = new ArrayList<String>();
    private static final List<String> quitCommands = Arrays.asList(
            new String[]{"quit", "bye", "exit", "close", "terminate", "shutdown",
                "hasta la vista", "vanakkam", "danyavada"});

    private Shutdownable shutdownable;
    
    public CliControlThread(Shutdownable shutdownable) {
        this.shutdownable = shutdownable;
    }

    @Override
    public void run() {
        while(true) {
            final String command = System.console().readLine();

            if(command == null || quitCommands.contains(command)) {
                shutdownable.shutdown();
                break;
            }
        }
    }
    
}
