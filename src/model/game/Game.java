package model.game;

import model.states.EndGame;
import server.ServerClientThread;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/*
    * This class is the main class of the game. It contains the game logic and the game state.
 */
public abstract class Game {
    private String secretWord;  // The secret word of the game
    private int tries;  // The number of tries the player has
    private int duration;  // The duration of the game
    private long startTime;  // The start time of the game
    private ArrayList<String> rightPlayedLetters;  // The letters that the player has played right
    private ArrayList<String> wrongPlayedLetters;  // The letters that the player has played wrong
    private ServerClientThread joueur1;  // The first player
    private boolean lose;  // If the player has lost
    private Timer timer;  // The timer of the game

    public static enum letterPlayed {  // The enum of the letter played
        RIGHT, WRONG, ALREADY_PLAYED
    }

    /**
     * Constructor of the game class with the secret word, the number of tries and the duration of the game
     */
    public Game(String secretWord, ServerClientThread joueur1) {
        this.secretWord = secretWord.toLowerCase();
        this.setDifficulty(joueur1.getDifficulty());
        this.joueur1 = joueur1;
        this.rightPlayedLetters = new ArrayList<>();
        this.wrongPlayedLetters = new ArrayList<>();

        this.lose = false;

        this.timer = new Timer();
        this.timer.schedule(new GameTimer(this), this.duration * 1000L);

        this.startTime = System.currentTimeMillis();

        System.out.println("Game created with secret word: " + this.secretWord);
    }


    /**
     * This method is called when the player plays a letter
     * @param letter The letter played
     * @return The enum of the letter played
     */
    public letterPlayed playLetter(String letter) {
        letter = letter.toLowerCase();
        if (rightPlayedLetters.contains(letter) || wrongPlayedLetters.contains(letter)) {
            return letterPlayed.ALREADY_PLAYED;
        }
        if (!secretWord.toLowerCase().contains(letter)) {
            removeTry(letter);
            return letterPlayed.WRONG;
        }
        rightPlayedLetters.add(letter);
        if (checkWin("LETTER_TRIED")) {
            this.timer.cancel();
            joueur1.sendMessage("You won !");
            joueur1.setEtat(new EndGame());
        }

        return letterPlayed.RIGHT;
    }

    /**
     * This method is called when the player plays a word to print the letters of the word
     * @return the word with his right letters
     */
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

    public ServerClientThread getJoueur1() {
        return joueur1;
    }

    public void setClient(ServerClientThread joueur1) {
        this.joueur1 = joueur1;
    }

    @Override
    public String toString() {
        return "------------------" + "Game : " + this.getReamingTime() + "s remains------------------" + "\n" +
                "  The word : " + this.generateWordWithLettersFound() + "\n" +
                "  Tries left : " + (tries - this.amountOfWrongPlayedLetters()) + "\n" +
                "  Right played letters : " + String.join(", ", this.rightPlayedLetters) + "\n" +
                "  Wrong played letters : " + String.join(", ", this.wrongPlayedLetters) + "\n" +
                "-------------------------------------------------" + "\n";
    }

    public int amountOfWrongPlayedLetters() {
        return this.wrongPlayedLetters.size();
    }

    /*
    Set the difficulty of the game
     */
    public void setDifficulty(int difficulty){
        switch (difficulty) {
            case 2 :
                this.tries = 6;
                this.duration = 90;
                break;
            case 3:
                this.tries = 4;
                this.duration = 60;
                break;
            default:  // Easy mode (case 1)
                this.tries = 10;
                this.duration = 120;
                break;

        }
    }

    /**
     * Check if the player has won the game
     * @param word The word played
     * @return If the player has won
     */
    public boolean checkWin(String word){
        if (word.equals("LETTER_TRIED")) return generateWordWithLettersFound().equals(secretWord);
        else return word.equals(secretWord);
    }

    /**
     * Method to try a word to guess the secret word
     * @param arg The tryed word
     */
    public boolean guessWord(String arg) {
        if (checkWin(arg)) {
            this.timer.cancel();
            joueur1.sendMessage("You won !");
            joueur1.setEtat(new EndGame());
            return true;
        } else {
            this.tries--;
            this.checkLose();
            return false;
        }
    }

    /**
     * Method to remove a try
     * @param letter The letter played
     */
    public void removeTry(String letter) {
        this.wrongPlayedLetters.add(letter);
        checkLose();
    }

    /**
     * Method to check if the player has lost
     */
    private void checkLose() {
        if (this.tries == wrongPlayedLetters.size()) {
            this.timer.cancel();
            joueur1.sendMessage("You lose...");
            this.lose = true;
            joueur1.setEtat(new EndGame());
        }
    }

    /**
     * Method to get the remaining time
     * @return The remaining time
     */
    private int getReamingTime() {
        return (int) (this.duration - (System.currentTimeMillis() - this.startTime) / 1000);
    }


    /**
     * Method to stop the timer
     */
    public void stop() {
        this.timer.cancel();
    }
}


