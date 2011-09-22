package org.wiztools.multicasttool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * The sniffer thread that collects data from the multicast IP and port and
 * writes it to stdout.
 * @author subwiz
 */
class MulticastSnifferThread implements Runnable, Shutdownable {
    
    private final InetAddress address;
    private final MulticastSocket socket;
    private final RuntimeOptions options;
    
    private boolean peacefulStop = false;

    public MulticastSnifferThread(InetAddress address,
            int port,
            RuntimeOptions options) throws IOException {
        socket = new MulticastSocket(port);
        socket.joinGroup(address);
        this.address = address;
        
        this.options = options;
    }
    
    @Override
    public void run() {
        byte[] buf = new byte[1024*64];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        
        try{
            while(true) {
                socket.receive(packet);
                if(options.isVerbose()) {
                    System.err.println("\nReceived from: " + packet.getSocketAddress());
                }
                System.out.write(packet.getData(), 0, packet.getLength());
            }
        }
        catch(IOException ex) {
            if(!peacefulStop) {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    @Override
    public void shutdown() {
        System.err.println("Shutdown request received.");
        peacefulStop = true;
        try{
            socket.leaveGroup(address);
            socket.close();
            socket.disconnect();
        }
        catch(IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
