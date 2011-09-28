package org.wiztools.multicasttool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The console reader implementation.
 * @author subwiz
 */
abstract class AbstractConsoleDataCollector implements DataCollector {

    @Override
    public abstract boolean hasMoreData();

    @Override
    public final byte[] getData() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        byte[] buf = new byte[1024*16];
        InputStream is = System.in;
        int i = -1;
        while((i=is.read(buf)) != -1) {
            baos.write(buf, 0, i);
        }
        return baos.toByteArray();
    }
    
}
