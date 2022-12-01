package model.states;

import model.command.BaseCommand;
import model.command.RestartCommand;
import model.command.ReturnToMenuCommand;
import model.command.SendMessageToOtherPlayerCommand;

public class EndGame extends Etat {

    private boolean win;
    private String secretWord;

    private BaseCommand restartCommand;
    private BaseCommand returnToMenuCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;

    public EndGame(boolean win, String secretWord) {
        super();
        this.win = win;
        this.secretWord = secretWord;
        this.restartCommand = new RestartCommand(this.quitCommand);
        this.returnToMenuCommand = new ReturnToMenuCommand(this.restartCommand);
        this.sendMessageToOtherPlayerCommand = new SendMessageToOtherPlayerCommand(this.returnToMenuCommand);
        this.command_available = sendMessageToOtherPlayerCommand;
    }

    @Override
    public String getClientInstruction() {
        if(win){
            return "Vous avez gagné en devinant le mot " + getSecretWord() + " ,Commandes disponibles :\n" +
                    this.command_available.toString();
        } else {
            return "Vous avez perdu, le mot était " + getSecretWord() + " ,Commandes disponibles:\n" +
                    this.command_available.toString();
        }
    }

    @Override
    public String toString() {
        return "EndGame";
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }
}
