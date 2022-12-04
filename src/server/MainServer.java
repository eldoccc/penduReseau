package server;

import model.game.Game;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
    Serveur side of hangman game setting up the server socket and waiting for client to connect
    then creating a new thread for each client and starting it
 */
public class MainServer {
    private static final int PORT = 1234;  // port to listen on

    /*
        Main method of the server
     */
    public static void main(String[] args) {
        ArrayList<ServerClientThread> serverClientThreadsInMenu = new ArrayList<>();  // list of all the client threads in the menu
        ArrayList<ServerClientThread> serverClientThreadsInQueue = new ArrayList<>();  // list of all the client threads in the queue
        ArrayList<Game> games = new ArrayList<>();  // list of all the games currently running

        /*
            Infinite loop to keep the server running and waiting for clients to connect
         */
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for a client ...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerClientThread serverClientThread = new ServerClientThread(clientSocket, serverClientThreadsInMenu, serverClientThreadsInQueue, games);
                serverClientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }
}
