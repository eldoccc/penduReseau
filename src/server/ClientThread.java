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

            System.out.println("End of thread");
        } catch (java.io.IOException e) {
            System.out.println("Error in ClientThread: " + e);
        }
    }

    public void run() {
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
            Response response = new Response(game.getTries() - game.amountOfPlayedLetters(), game.generateWordWithLettersFound(), stateRound);

            // Send the word with the letters found
            try {
                oos.writeObject(response);
            } catch (java.io.IOException e) {
                return;
            }
        }
    }
}
