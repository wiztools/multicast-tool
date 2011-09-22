package org.wiztools.multicasttool;

import java.io.BufferedReader;
import java.io.IOException;
import org.wiztools.commons.Charsets;

/**
 * The console reader implementation.
 * @author subwiz
 */
abstract class AbstractConsoleDataCollector implements DataCollector {

    @Override
    public abstract boolean hasMoreData();

    @Override
    public final byte[] getData() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(System.console().reader());
        String str = null;
        while((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString().getBytes(Charsets.UTF_8);
    }
    
}
