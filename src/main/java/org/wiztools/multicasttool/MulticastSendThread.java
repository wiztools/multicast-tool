package org.wiztools.multicasttool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Class to send packet to multicast address
 * @author subwiz
 */
class MulticastSendThread implements Runnable {
    
    private final InetAddress address;
    private final int port;
    private final DataCollector dataCollector;

    MulticastSendThread(InetAddress address, int port, DataCollector collector) {
        this.address = address;
        this.port = port;
        this.dataCollector = collector;
    }

    @Override
    public void run() {
        try {
            MulticastSocket soc = new MulticastSocket(port);
            while(dataCollector.hasMoreData()) {
                byte[] buf = dataCollector.getData();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                soc.send(packet);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
    
}
