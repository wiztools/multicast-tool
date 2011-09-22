package org.wiztools.multicasttool;

/**
 * Collects data one time from the console.
 * @author subwiz
 */
class ConsoleDataCollector extends AbstractConsoleDataCollector {
    
    private boolean firstRun = true;

    @Override
    public boolean hasMoreData() {
        if(firstRun) {
            firstRun = false;
            return true;
        }
        return false;
    }

}
