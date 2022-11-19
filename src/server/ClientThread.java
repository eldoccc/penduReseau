package server;

import model.Game;
import model.Response;
import model.Wording;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private Game game;
    private BufferedReader in;
    private PrintStream out;
    private ObjectOutputStream oos;
    private Wording wording;
    private ArrayList<ClientThread> clientThreads;

    public ClientThread(Socket clientSocket, ArrayList<ClientThread> clientThreads, int i) throws FileNotFoundException {
        this.clientSocket = clientSocket;
        this.clientThreads = clientThreads;
        if (i == 0) {
            java.io.File file = new java.io.File("message.txt");
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            try {
                //Test the clientThread sync with the new clients
                output.println(this.clientThreads.size());
                while (true) {
                    output.println(this.clientThreads.size());
                    Thread.sleep(1000);
                }

            /*this.in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintStream(clientSocket.getOutputStream());
            this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
            this.wording = new Wording();
            // test area
            this.game = new Game(wording.getRandomWord(), clientSocket);
            //this.game.setTries(14);
            // end of test area

            this.run();

            System.out.println("End of thread");*/
            } catch (InterruptedException e) {
                output.println("Error in ClientThread: " + e);
                e.printStackTrace();
            }
        }
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
