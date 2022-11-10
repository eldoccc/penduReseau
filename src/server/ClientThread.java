package server;

import model.Game;
import model.Response;
import model.Wording;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket clientSocket;
    private Game game;
    private BufferedReader in;
    private PrintStream out;
    private ObjectOutputStream oos;
    private Wording wording;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
            this.wording = new Wording();
            // test area
            this.game = new Game(wording.getRandomWord(), clientSocket);
            //this.game.setTries(14);
            // end of test area

            this.run();

            System.out.println("End of thread");
        } catch (java.io.IOException e) {
            System.out.println("Error in ClientThread: " + e);
        }
    }

    public void run() {

        while (!game.isLose() || clientSocket.isClosed()) {
            if(game.getTries() == 0){
                try {
                    game.setDifficulty(Integer.parseInt(in.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
}
