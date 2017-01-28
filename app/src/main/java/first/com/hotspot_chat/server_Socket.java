package first.com.hotspot_chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Test on 1/28/2017.
 */

public class server_Socket {
    public static final int PORT = 2024;

    //Server Socket declaration
    ServerSocket serversocket = null;
    Socket socket = null;

    try

    {
        //  Initialising the ServerSocket with input as port number
        try {
            serversocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            // makes a socket connection for clients
            // accept method waiting for connection(listening mode)
            socket = serversocket.accept();

            // Receive message from client
            DataInputStream dis = new
                    DataInputStream(socket.getInputStream());

// for reading dis.readLine();

            // Send message to the client

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("Hello User");
            dos.close();

            // Close the Socket connection
            socket.close();
        } catch (SocketException socketexception) {
            System.out.println("Server problem  " + socketexception.getMessage());
        } catch (Exception exception) {
            System.out.println("Something wrong error occure" + exception.getMessage());
        }

        //get address of socket
        System.out.println(" Connection from :  " + socket.getInetAddress());

    }
}
