package org.wiztools.multicasttool;

import java.io.Console;

/**
 * Collect interactively data to send from the console.
 * @author subwiz
 */
class ConsoleInteractiveDataCollector extends AbstractConsoleDataCollector {
    
    private boolean firstRun = true;

    @Override
    public boolean hasMoreData() {
        if(firstRun) {
            firstRun = false;
            return true;
        }
        Console console = System.console();
        if(console != null) { // Only if console is available
            System.out.print("Enter more data (y/n): ");
            String in = console.readLine();
            if(in.equals("y"))
                return true;
        }
        return false;
    }
}
