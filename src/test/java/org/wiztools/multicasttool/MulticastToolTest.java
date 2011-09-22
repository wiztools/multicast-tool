package org.wiztools.multicasttool;

import java.net.InetAddress;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author subwiz
 */
public class MulticastToolTest {
    
    public MulticastToolTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void hello() throws Exception {
        final int port = 8000;
        final InetAddress address = InetAddress.getByName("239.255.255.255");
        
        RuntimeOptionsImpl rtOpts = new RuntimeOptionsImpl();
        rtOpts.setVerbose(true);
        MulticastSnifferThread t = new MulticastSnifferThread(address, port, rtOpts);
        new Thread(t).start();
        
        // MulticastSendThread t1 = new MulticastSendThread(address, port, new ConsoleDataCollector());
        // t1.run();
        
        t.shutdown();
        
    }
}
