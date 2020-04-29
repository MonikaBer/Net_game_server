package server.app;

import java.net.*;

public class Main  {

  public static void main(String[] args) {

    ServerSocket serverSocketTCP;
    DatagramSocket serverSocketUDP;
    InetSocketAddress serverAddress;
    Socket clientSocket;
    int clientNumber = 0;

    try {
      serverAddress = new InetSocketAddress("localhost", 7000);
      serverSocketUDP = new DatagramSocket(serverAddress);
      new UdpThread(serverSocketUDP).start();

      serverSocketTCP = new ServerSocket(8000);
      while (true) {
        clientSocket = serverSocketTCP.accept();
        clientNumber++;
        new TcpThread(clientSocket, clientNumber).start();
      }

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
