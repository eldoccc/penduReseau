package model.states;

import model.command.BaseCommand;
import model.command.SendLetterCommand;

public class InGame extends Etat {

    private BaseCommand sendLetterCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;
    private BaseCommand guessWordCommand;

    private int letterState;
    /*
     0 = already use
     1 = letter match
     2 = lose
     3 = win
     4 = letter not match
    */
    private int triesLeft;
    private String hiddenWord;


    public InGame() {
        super();
        this.sendLetterCommand = new SendLetterCommand(this.quitCommand);
        this.sendMessageToOtherPlayerCommand = new SendLetterCommand(this.sendLetterCommand);
        this.guessWordCommand = new SendLetterCommand(this.sendMessageToOtherPlayerCommand);
        this.command_available = guessWordCommand;

    }

    @Override
    public String getClientInstruction() {
        return "Vous êtes en jeu ! Commandes :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "InGame";
    }

    public int getLetterState() {
        return letterState;
    }

    public void setLetterState(int letterState) {
        this.letterState = letterState;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }
}


