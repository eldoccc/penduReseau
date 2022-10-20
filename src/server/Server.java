package server;

import client.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
    Serveur side of hangman game setting up the server socket and waiting for client to connect
    then creating a new thread for each client
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Server started. Waiting for a client ...");
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            new Thread(new ClientThread(clientSocket)).start();
        }
    }

}
