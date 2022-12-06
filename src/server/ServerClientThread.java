package server;

import model.Response;
import model.command.CommandException;
import model.game.Game;
import model.game.MultiplayerGame;
import model.states.Etat;
import model.states.InQueue;
import model.states.Menu;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*
    Thread of a single client connected to the server handling the communication between the client and the server
 */
public class ServerClientThread extends Thread {
    private String name;  // Name of the player
    protected int difficulty;  // Difficulty selected by the player (default 1)
    private boolean isGuesser;  // Boolean to know if the player is the guesser or the decider
    private ServerClientThread playerAsked;  // Player asked by the player to play with (null if no player asked)
    private Socket clientSocket;  // Socket of the client
    private BufferedReader in;  // Input stream of the client
    private PrintStream out;  // Output stream of the client
    private ObjectOutputStream oos;  // Object output stream of the client
    private ObjectInputStream ois;  // Object input stream of the client
    private ArrayList<ServerClientThread> menuClients;  // List of all the clients in the menu sync with the list in the server
    private ArrayList<ServerClientThread> queueClients;  // List of all the clients in the queue sync with the list in the server
    private ArrayList<Game> games;  // List of all the games currently running sync with the list in the server
    private Game currentGame;  // Game that contains the player (null if the player isn't playing)
    private Etat state;  // State of the player (menu, queue, game)


    /*
        Constructor of the thread of a client
     */
    public ServerClientThread(Socket clientSocket, ArrayList<ServerClientThread> mC, ArrayList<ServerClientThread> qC, ArrayList<Game> g) {
        this.clientSocket = clientSocket;
        this.menuClients = mC;
        this.queueClients = qC;
        this.games = g;
        this.difficulty = 1;  // default difficulty (esay mode)
        this.isGuesser = true;  // default role (guesser)
        try {
            /*
                Creating the input and output streams of the client
             */
            this.in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            this.ois = new ObjectInputStream(clientSocket.getInputStream());
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());

            this.name = in.readLine();  // Receive the name of the player from the client
            System.out.println("Player " + this.name + " connected");
            this.menuClients.add(this);  // Add the player to the list of players in the menu

            this.setEtat(new Menu());  // Set the state of the player to menu


            this.oos.writeObject(new Response("Welcome to the game " + this.name, this.state));  // Init the player to the menu and also confirm the connection
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    this.oos.writeObject(new Response("Goodbye", this.state));
                    break;
                }

                // Execute the command
                Response response;
                try {
                    response = this.state.execute(command, this);
                } catch (CommandException e) {
                    response = new Response(e.getMessage(), this.state);
                }


                // Send the response to the client if there is one
                if (response != null) {
                    this.oos.writeObject(response);

                    // If the client's state change, change the local state and print the commands (normally it will never happen because the state change is done in the execute method)
                    if (response.getState() != this.state) {
                        this.setEtat(response.getState());
                        System.out.println(this.state.getClientInstruction());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print the error if there is one
        } finally {
            try {
                end();  // Try to close all the streams and the socket of the client
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
        Method to close all the streams and the socket of the client
     */
    public void end() throws IOException {
        if (this.playerAsked != null) {
            this.playerAsked.sendMessage("The invited player has left the game");
            this.playerAsked.setPlayerAsked(null);
        }

        // If the player is in a game, reset the opponent if multiplayer and remove the game
        if (this.currentGame != null) {
            if (this.currentGame instanceof MultiplayerGame) {
                MultiplayerGame tmp = (MultiplayerGame) this.currentGame;
                if (this.isGuesser) {
                    tmp.getDecider().setGame(null);
                    tmp.getDecider().setEtat(new Menu());
                    tmp.getDecider().joinMenu();
                    tmp.getDecider().sendMessage("Your opponent has left the game");
                } else {
                    tmp.getGuesser().setGame(null);
                    tmp.getGuesser().setEtat(new Menu());
                    tmp.getGuesser().joinMenu();
                    tmp.getGuesser().sendMessage("Your opponent has left the game");
                }
            }
            this.currentGame.stop();
            this.games.remove(this.currentGame);
        }

        /*
            Close all the streams and the socket
         */
        this.clientSocket.close();
        this.in.close();
        this.out.close();
        this.oos.close();
        this.ois.close();

        try {
            this.menuClients.remove(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.queueClients.remove(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Player " + this.name + " disconnected");
    }

    /*
        Method to send a unified message to the client
     */
    public void sendResponse(Response response) {
        try {
            this.oos.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
        Method to send a message to all the players in the menu
     */
    public void sendMessageGeneral(String message) {
        for (ServerClientThread sct : this.menuClients) {
            sct.sendMessage("General from " + this.name + ": " + message);
        }
    }

    /*
        Method to send a message to a specific player (never used but could be an improvement)
     */
    public void sendMessageToOtherPlayer(String message,String receiver) {
        for (ServerClientThread sct : this.menuClients) {
            if (sct.name.equals(receiver)) {
                sct.sendMessage("Private from " + this.name + ": " + message);
            }
        }
    }

    /*
        Method to send a basic message to the client
     */
    public void sendMessage(String message) {
        try {
            this.oos.writeObject(new Response(message, this.state));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Etat getEtat() {
        return this.state;
    }
    public ServerClientThread getPlayerAsked() {
        return this.playerAsked;
    }
    public void setPlayerAsked(ServerClientThread playerAsked) {
        this.playerAsked = playerAsked;
    }
    public boolean isGuesser() {
        return this.isGuesser;
    }
    public void setGuesser(boolean isGuesser) {
        this.isGuesser = isGuesser;
    }
    public String getPlayerName() {
        return this.name;
    }
    public void setEtat(Etat etat) {
        this.state = etat;
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

    /*
        Method to get the player in the queue with the name
     */
    public ServerClientThread getPlayerByNameInQueue(String name) {
        for (ServerClientThread sct : this.queueClients) {
            if (sct.name.equals(name)) {
                return sct;
            }
        }
        return null;
    }

    /*
        Method to join the queue
     */
    public void joinQueue() {
        this.menuClients.remove(this);
        if (!this.queueClients.contains(this)) {
            this.queueClients.add(this);
        }
        this.setEtat(new InQueue());

        // Inform all players in the queue that a new player joined
        for (ServerClientThread sct : this.queueClients) {
            if (sct.isGuesser != this.isGuesser) {
                sct.sendMessage(this.name + " joined the queue");
            }
        }
    }

    /*
        Method to leave the queue
     */
    public void leaveQueue() {
        this.queueClients.remove(this);
        this.menuClients.add(this);
        this.setEtat(new Menu());

        // Inform all players in the queue that a player left
        for (ServerClientThread sct : this.queueClients) {
            if (sct.isGuesser != this.isGuesser) {
                sct.sendMessage(this.name + " left the queue");
            }
        }
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
    }

    public Game getGame() {
        return this.currentGame;
    }
    public void setGame(Game game) {
        this.currentGame = game;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    /*
    Method to leave the menu
     */
    public void leaveMenu() {
        this.menuClients.remove(this);
    }

    /*
        Method to join the menu
     */
    public void joinMenu() {
        if (!this.menuClients.contains(this)) {
            this.menuClients.add(this);
        }
    }

    public String toString() {
        return "   " + this.name + " - Difficulty: " + this.difficulty;
    }
}
