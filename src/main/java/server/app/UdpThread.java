package server.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class UdpThread extends java.lang.Thread {

    private DatagramSocket serverDatagramSocket;
    private String clientAddress;
    private int clientPort;
    private byte[] buff;
    private DatagramPacket packet;

    public UdpThread(DatagramSocket servDatagramSock) {
        this.serverDatagramSocket = servDatagramSock;
    }


    public void run() {
        byte[] buff = new byte[50];
        int len;

        packet = new DatagramPacket(buff, buff.length);
        try {
            serverDatagramSocket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println(received);
    }
}
