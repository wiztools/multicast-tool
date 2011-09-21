package org.wiztools.multicasttool;

import java.io.IOException;

/**
 *
 * @author subwiz
 */
interface DataCollector {
    boolean hasMoreData();
    
    byte[] getData() throws IOException;
}
