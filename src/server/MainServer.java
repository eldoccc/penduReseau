package server;

import model.game.Game;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
    Serveur side of hangman game setting up the server socket and waiting for client to connect
    then creating a new thread for each client
 */
public class MainServer {
    private static final int PORT = 1234;


    public static void main(String[] args) {
        ArrayList<ServerClientThread> serverClientThreadsInMenu = new ArrayList<>();
        ArrayList<ServerClientThread> serverClientThreadsInQueue = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();

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
