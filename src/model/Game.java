package model;

import java.net.Socket;
import java.util.ArrayList;

public class Game {
    private String secretWord;
    private int tries;
    private ArrayList<String> rightPlayedLetters;
    private ArrayList<String> wrongPlayedLetters;
    private Socket client;
    private boolean lose;

    public Game(String secretWord, Socket client) {
        this.secretWord = secretWord.toLowerCase();
        this.tries = 0;
        this.client = client;
        this.rightPlayedLetters = new ArrayList<>();
        this.wrongPlayedLetters = new ArrayList<>();

        this.lose = false;
    }

    /*
     0 = already use
     1 = letter match
     2 = lose
     3 = win
     4 = letter not match
    */
    public int playLetter(String letter) {
        letter = letter.toLowerCase();
        if (rightPlayedLetters.contains(letter)) {
            return 0;
        }
        //playedLetters.add(letter);
        if (!secretWord.toLowerCase().contains(letter)) {
            wrongPlayedLetters.add(letter);
            if (tries == wrongPlayedLetters.size()) {
                lose = true;
                return 2;
            }
            return 4;
        }
        rightPlayedLetters.add(letter);
        if (generateWordWithLettersFound().equals(secretWord)) {
            return 3;
        }

        return 1;
    }

    public String generateWordWithLettersFound() {
        String word = "";
        if (lose) {
            return secretWord;
        } else {
            for (int i = 0; i < secretWord.length(); i++) {
                if (rightPlayedLetters.contains(secretWord.toLowerCase().substring(i, i + 1))) {
                    word += secretWord.substring(i, i + 1);
                } else {
                    word += "_";
                }
            }
        }
        return word;
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

    public ArrayList<String> getRightPlayedLetters() {
        return rightPlayedLetters;
    }

    public void setRightPlayedLetters(ArrayList<String> rightPlayedLetters) {
        this.rightPlayedLetters = rightPlayedLetters;
    }

    public ArrayList<String> getWrongPlayedLetters() {
        return wrongPlayedLetters;
    }

    public void setWrongPlayedLetters(ArrayList<String> wrongPlayedLetters) {
        this.wrongPlayedLetters = wrongPlayedLetters;
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
                ", rightPlayedLetters=" + rightPlayedLetters +
                ", wrongPlayedLetters=" + wrongPlayedLetters +
                ", client=" + client +
                ", lose=" + lose +
                '}';
    }

    public int amountOfWrongPlayedLetters() {
        return this.wrongPlayedLetters.size();
    }

    public void setDifficulty(int difficulty){
        switch (difficulty) {
            case 1 :
                this.tries = 10;
                break;
            case 2 :
                this.tries = 6;
                break;
            case 3:
                this.tries = 4;
                break;
            default:
                this.tries = 10;
                break;

        }
    }
}


