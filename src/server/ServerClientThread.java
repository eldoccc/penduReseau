package server;

import com.sun.security.ntlm.Server;
import model.command.CommandException;
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
    private ArrayList<ServerClientThread> menuClients;
    private ArrayList<ServerClientThread> queueClients;
    private Etat state;

    private Game game;
    private Wording wording;


    public ServerClientThread(Socket clientSocket, ArrayList<ServerClientThread> mC, ArrayList<ServerClientThread> qC) {
        this.clientSocket = clientSocket;
        this.menuClients = mC;
        this.queueClients = qC;
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
                Response2 response;
                try {
                    response = this.state.execute(command, this);
                } catch (CommandException e) {
                    response = new Response2(e.getMessage(), this.state);
                }


                // Send the response to the client if there is one
                if (response != null) {
                    this.oos.writeObject(response);

                    // If the client's state change, change the local state and print the commands
                    if (response.getState() != this.state) {
                        this.changeState(response.getState());
                        System.out.println(this.state.getClientInstruction());
                    }
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

        this.serverClientThreads.remove(this);

        System.out.println("Player " + this.name + " disconnected");
    }


    public void sendMessageGeneral(String message) {
        for (ServerClientThread sct : this.menuClients) {
            sct.sendMessage("General from " + this.name + ": " + message);
        }
    }

    private void sendMessage(String message) {
        try {
            this.oos.writeObject(new Response2(message, this.state));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Etat getEtat() {
        return this.state;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<ServerClientThread> getPlayerInMenu() {
        return menuClients;
    }

    public ArrayList<ServerClientThread> getPlayerInQueue() {
        return this.queueClients;
    }

    public String printPlayersInQueue() {
        // Return a string with the list of players in the state InQueue with their name and difficulty and index to choose them
        String players = "";
        int i = 0;
        for (ServerClientThread sct : this.queueClients) {
            players += i + " - " + sct.name + " - Difficulty: " + sct.difficulty + "\n";
            i++;
        }
        return players;
    }
}
