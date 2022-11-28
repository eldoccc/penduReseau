package server;

import model.Game;
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
        try {
            game.setDifficulty(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!game.isLose() || clientSocket.isClosed()) {

            // Receive a letter from the client
            String letter = "";
            try {
                while (letter.length() != 1) {
                    letter = in.readLine();
                }
            } catch (Exception e) {
                return;
            }

            // Play the letter
            int stateRound = this.game.playLetter(letter);
            Response response = new Response(game.getTries() - game.amountOfWrongPlayedLetters(), game.generateWordWithLettersFound(), stateRound);

            // Send the word with the letters found
            try {
                oos.writeObject(response);
            } catch (java.io.IOException e) {
                return;
            }
        }
    }

    public String getSecretWord() {
        return this.game.getSecretWord();
    }
}
