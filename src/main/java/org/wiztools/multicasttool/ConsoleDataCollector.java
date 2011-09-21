package org.wiztools.multicasttool;

import java.io.BufferedReader;
import java.io.IOException;
import org.wiztools.commons.Charsets;

/**
 * Collect data to send from the console.
 * @author subwiz
 */
class ConsoleDataCollector implements DataCollector {
    
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

    @Override
    public byte[] getData() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(System.console().reader());
        String str = null;
        while((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString().getBytes(Charsets.UTF_8);
    }
    
}
