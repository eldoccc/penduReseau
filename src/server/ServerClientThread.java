package server;

import model.game.Game;
import model.Response;
import model.Response2;
import model.Wording;
import model.states.Etat;
import model.states.Menu;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClientThread extends Thread {
    private String name;
    protected int difficulty;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintStream out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ArrayList<ServerClientThread> serverClientThreads;
    private Etat state;

    private Game game;
    private Wording wording;


    public ServerClientThread(Socket clientSocket, ArrayList<ServerClientThread> otherClients) {
        this.clientSocket = clientSocket;
        this.serverClientThreads = otherClients;
        this.difficulty = 1;
        try {
            this.in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            this.ois = new ObjectInputStream(clientSocket.getInputStream());
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());

            // Get the player name
            this.name = in.readLine();
            System.out.println("Player " + this.name + " connected");

            this.changeState(new Menu());

            // Init the player to the menu and also confirm the connection
            this.oos.writeObject(new Response2("Welcome to the game " + this.name, this.state));

            //this.run();

            System.out.println("End of thread");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeState(Etat state) {
        this.state = state;
    }

    public void run() {
        // Infinite loop to get the client commands
        try {
            while (true) {
                // Get the command from the client
                String command = in.readLine();
                System.out.println("Command received: " + command);

                // If the command is quit then quit the game
                if (command.equals("quit")) {
                    this.oos.writeObject(new Response2("Goodbye", this.state));
                    break;
                }

                // Execute the command
                Response2 response = this.state.execute(command, this);

                // Send the response to the client
                this.oos.writeObject(response);

                // If the client's state change, change the local state and print the commands
                if (response.getState() != this.state) {
                    this.changeState(response.getState());
                    System.out.println(this.state.getClientInstruction());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                end();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void end() throws IOException {
        this.clientSocket.close();
        this.in.close();
        this.out.close();
        this.oos.close();
        this.ois.close();
    }

    public Etat getEtat() {
        return this.state;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
