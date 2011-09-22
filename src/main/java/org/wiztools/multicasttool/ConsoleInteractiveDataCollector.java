package org.wiztools.multicasttool;

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
        System.out.print("Enter more data (y/n): ");
        String in = System.console().readLine();
        if(in.equals("y"))
            return true;
        return false;
    }
}
