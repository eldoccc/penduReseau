package client;

import model.command.QuitCommand;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * This class is the client of the game
 */
public class Client {

    /**
     * The main method of the client
     * @param args The arguments of the main method (the ip address and the port)
     * @throws IOException If there is an error with the input or the output
     * @throws ClassNotFoundException If there is an error with the class
     */
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
            clientThread.send(command);  // Send the command to the server
        }

        clientThread.end();

        // Confirm the end of the client
        System.out.println("End of client");
    }
}
