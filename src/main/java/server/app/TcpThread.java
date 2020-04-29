package server.app;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class TcpThread extends java.lang.Thread {

    private Socket clientSocket;
    private int number;

    //PrintWriter out;
    //BufferedReader in;
    InputStream in;
    OutputStream out;
    byte[] buff;
    String serverPublicKey;
    String serverPrivateKey;
    String clientPublicKey;
    String sessionKey;

    public TcpThread(Socket cs, int nr) {

        this.clientSocket = cs;
        this.number = nr;
    }


    public void run() {
        //out = new PrintWriter(clientSocket.getOutputStream(), true);
        //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        try {
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }



        byte resp = 0;
        try {
            out.write(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Zgłosił się klient");

        buff = new byte[50];

        //negocjacja kluczy
        serverPublicKey = "mnfgooaahh";
        //serverPrivateKey = ;

        while(true) {                                  //oczekiwanie na klucz publiczny klienta
            int len = 0;
            try {
                len = in.read(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (len == -1) break;  //błąd

            if (len == 10) {                             //odebrano klucz publiczny klienta
                StringBuilder sBuilder = new StringBuilder();
                for(int i = 0; i < len; i++) {
                    sBuilder.append((char)buff[i]);
                }

                clientPublicKey = sBuilder.toString();
                System.out.println(clientPublicKey);
                break;
            }
        }

        try {
            out.write(serverPublicKey.getBytes());       //wysłanie klucza publicznego serwera do klienta
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {                              //oczekiwanie na klucz sesji od klienta
            int len = 0;
            try {
                len = in.read(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (len == -1) break;  //błąd

            if (len == 10) {                                        //odebrano klucz sesji
                StringBuilder sBuilder = new StringBuilder();
                for(int i = 0; i < len; i++) {
                    sBuilder.append((char)buff[i]);
                }

                sessionKey = sBuilder.toString();
                System.out.println(sessionKey);
                break;
            }
        }

        while (true) {
            int len = 0;
            try {
                len = in.read(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (len == -1) break;  //błąd

            if (len == 5) {                                        //odebrano datagram mówiący o aktywności klienta
                StringBuilder sBuilder = new StringBuilder();
                for(int i = 0; i < len; i++) {
                    sBuilder.append((char)buff[i]);
                }

                System.out.println(sBuilder.toString());
                break;
            }
        }
    }
}