package org.wiztools.multicasttool;

import java.io.IOException;

/**
 * Interface to collect data.
 * @author subwiz
 */
interface DataCollector {
    boolean hasMoreData();
    
    byte[] getData() throws IOException;
}
