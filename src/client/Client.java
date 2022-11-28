package client;

import model.Response;
import model.command.QuitCommand;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket clientSocket;
        BufferedReader in_keyboard;

        // Get the adresse ip and the port with args
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        // Ask for the player name
        in_keyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        String name = in_keyboard.readLine();
        System.out.println("");

        // Create the socket
        clientSocket = new Socket(ip, port);


        // Create the Client Thread for listen the server
        ClientThread clientThread = new ClientThread(clientSocket, name);
        clientThread.start();

        // Create the infite loop to record the console input
        String command = "";
        while (!Objects.equals(command, QuitCommand.QUIT_COMMAND_PUBLIC)) {
            command = in_keyboard.readLine();
            clientThread.send(command);
        }

        clientThread.end();

        // Confirm the end of the client
        System.out.println("End of client");
    }
}
