package model.states;

import model.command.BaseCommand;
import model.command.SendLetterCommand;

public class InGame extends Etat {

    private BaseCommand sendLetterCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;
    private BaseCommand guessWordCommand;

    private int triesLeft;
    private String hiddenWord;


    public InGame(int triesLeft, String hiddenWord) {
        super();
        this.triesLeft = triesLeft;
        this.hiddenWord = hiddenWord;
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


