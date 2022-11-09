package server;

import model.Game;
import model.Response;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket clientSocket;
    private Game game;
    private BufferedReader in;
    private PrintStream out;
    private ObjectOutputStream oos;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());

            this.game = new Game("Hemdoulila", clientSocket);
            this.run();
        } catch (java.io.IOException e) {
            System.out.println("Error in ClientThread: " + e);
        }
    }

    @Override
    public void run() {
        // Send the word to guess
        out.println(game.getSecretWord());

        while (!game.isLose() || clientSocket.isClosed()) {
            // Receive a letter from the client
            String letter = "";
            while (letter.length() != 1) {
                try {
                    letter = in.readLine();
                } catch (java.io.IOException e) {
                    System.out.println("Error in ClientThread: " + e);
                }
            }

            Response response = new Response(game.getTries() - game.amountOfPlayedLetters(), game.generateWordWithLettersFound(), -1);

            if (this.game.playLetter(letter)) {
                response.setStateGame(1);
            } else {
                response.setStateGame(0);
            }

            if (game.isLose()) {
                response.setStateGame(2);
            } else if (game.generateWordWithLettersFound().equals(game.getSecretWord())) {
                response.setStateGame(3);
            }

            // Send the word with the letters found
            try {
                oos.writeObject(response);
            } catch (java.io.IOException e) {
                System.out.println("Error in ClientThread: " + e);
            }
        }
    }
}
