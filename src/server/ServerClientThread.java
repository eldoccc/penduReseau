package server;

import model.command.CommandException;
import model.game.Game;
import model.Response2;
import model.Wording;
import model.game.MultiplayerGame;
import model.states.Etat;
import model.states.InQueue;
import model.states.Menu;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClientThread extends Thread {
    private String name;
    protected int difficulty;
    private boolean isGuesser;
    private ServerClientThread playerAsked;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintStream out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ArrayList<ServerClientThread> menuClients;
    private ArrayList<ServerClientThread> queueClients;
    private ArrayList<Game> games;
    private Game currentGame;
    private Etat state;

    private Wording wording;


    public ServerClientThread(Socket clientSocket, ArrayList<ServerClientThread> mC, ArrayList<ServerClientThread> qC, ArrayList<Game> g) {
        this.clientSocket = clientSocket;
        this.menuClients = mC;
        this.queueClients = qC;
        this.games = g;
        this.difficulty = 1;
        this.isGuesser = true;
        try {
            this.in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            this.ois = new ObjectInputStream(clientSocket.getInputStream());
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());

            // Get the player name
            this.name = in.readLine();
            System.out.println("Player " + this.name + " connected");
            this.menuClients.add(this);

            this.setEtat(new Menu());

            // Init the player to the menu and also confirm the connection
            this.oos.writeObject(new Response2("Welcome to the game " + this.name, this.state));
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
                        this.setEtat(response.getState());
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

    public void sendResponse(Response2 response) {
        try {
            this.oos.writeObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessageGeneral(String message) {
        for (ServerClientThread sct : this.menuClients) {
            sct.sendMessage("General from " + this.name + ": " + message);
        }
    }

    public void sendMessageToOtherPlayer(String message,String receiver) {
        for (ServerClientThread sct : this.menuClients) {
            if (sct.name.equals(receiver)) {
                sct.sendMessage("Private from " + this.name + ": " + message);
            }
        }
    }

    public void sendMessage(String message) {
        try {
            this.oos.writeObject(new Response2(message, this.state));
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

    public ServerClientThread getPlayerByNameInQueue(String name) {
        for (ServerClientThread sct : this.queueClients) {
            if (sct.name.equals(name)) {
                return sct;
            }
        }
        return null;
    }

    public void sendCustomMessageToAPlayer(String message, String playerName) {
        for (ServerClientThread sct : this.menuClients) {
            if (sct.name.equals(playerName)) {
                sct.sendMessage(message);
            }
        }
        for (ServerClientThread sct : this.queueClients) {
            if (sct.name.equals(playerName)) {
                sct.sendMessage(message);
            }
        }
    }

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

    public void leaveMenu() {
        this.menuClients.remove(this);
    }

    public void joinMenu() {
        if (!this.menuClients.contains(this)) {
            this.menuClients.add(this);
        }
    }

    public String toString() {
        return "   " + this.name + " - Difficulty: " + this.difficulty;
    }
}
