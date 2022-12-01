package model.game;

import model.states.EndGame;
import server.ServerClientThread;

import java.net.Socket;
import java.util.ArrayList;

public abstract class Game {
    private String secretWord;
    private int tries;
    private ArrayList<String> rightPlayedLetters;
    private ArrayList<String> wrongPlayedLetters;
    private ServerClientThread joueur1;
    private boolean lose;

    public static enum letterPlayed {
        RIGHT, WRONG, ALREADY_PLAYED
    }

    public Game(String secretWord, ServerClientThread joueur1) {
        this.secretWord = secretWord.toLowerCase();
        this.tries = 0;
        this.joueur1 = joueur1;
        this.rightPlayedLetters = new ArrayList<>();
        this.wrongPlayedLetters = new ArrayList<>();

        this.lose = false;
    }

    public letterPlayed playLetter(String letter) {
        letter = letter.toLowerCase();
        if (rightPlayedLetters.contains(letter)) {
            return letterPlayed.ALREADY_PLAYED;
        }
        if (!secretWord.toLowerCase().contains(letter)) {
            removeTry(letter);
            return letterPlayed.WRONG;
        }
        rightPlayedLetters.add(letter);
        checkWin("LETTER_TRIED");

        return letterPlayed.RIGHT;
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
    }à

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

    public ServerClientThread getJoueur1() {
        return joueur1;
    }

    public void setClient(ServerClientThread joueur1) {
        this.joueur1 = joueur1;
    }

    @Override
    public String toString() {
        return "Game{" +
                "secretWord='" + secretWord + '\'' +
                ", tries=" + tries +
                ", rightPlayedLetters=" + rightPlayedLetters +
                ", wrongPlayedLetters=" + wrongPlayedLetters +
                ", client=" + joueur1 +
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

    public boolean checkWin(String word){
        if (word.equals("LETTER_TRIED")) return generateWordWithLettersFound().equals(secretWord);
        else return word.equals(secretWord);
    }

    public boolean guessWord(String arg) {
        if (checkWin(arg)) {
            joueur1.setEtat(new EndGame(true,secretWord));
            return true;
        } else {
            removeTry("$");
            return false;
        }
    }

    public void removeTry(String letter) {
        this.wrongPlayedLetters.add(letter);
        if (this.tries == wrongPlayedLetters.size()) {
            joueur1.setEtat(new EndGame(false,secretWord));
        }
    }
}


