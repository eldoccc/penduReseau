package model;

import java.net.Socket;
import java.util.ArrayList;

public class Game {
    private String secretWord;
    private int tries;
    private ArrayList<String> playedLetters;
    private Socket client;
    private boolean lose;

    public Game(String secretWord, Socket client) {
        this.secretWord = secretWord;
        this.tries = 0;
        this.client = client;
        this.playedLetters = new ArrayList<>();
        this.lose = false;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public ArrayList<String> getPlayedLetters() {
        return playedLetters;
    }

    public void setPlayedLetters(ArrayList<String> playedLetters) {
        this.playedLetters = playedLetters;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Game{" +
                "secretWord='" + secretWord + '\'' +
                ", tries=" + tries +
                ", playedLetters=" + playedLetters +
                ", client=" + client +
                '}';
    }

    public int amountOfPlayedLetters() {
        return this.playedLetters.size();
    }

    public void wrongLetter(String letter) {
        this.playedLetters.add(letter);
        if (this.tries == amountOfPlayedLetters()) lose = true;
    }
}


