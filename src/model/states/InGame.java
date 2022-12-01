package model.states;

import model.command.BaseCommand;
import model.command.GuessWordCommand;
import model.command.SendLetterCommand;
import model.command.SendMessageToOtherPlayerCommand;

public class InGame extends Etat {

    private BaseCommand sendLetterCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;
    private BaseCommand guessWordCommand;


    public InGame(boolean isGuesser) {
        super();
        if (isGuesser) {
            this.sendLetterCommand = new SendLetterCommand(this.quitCommand);
            this.guessWordCommand = new GuessWordCommand(this.sendLetterCommand);
            this.sendMessageToOtherPlayerCommand = new SendMessageToOtherPlayerCommand(this.guessWordCommand);
        } else {
            this.sendMessageToOtherPlayerCommand = new SendMessageToOtherPlayerCommand(this.quitCommand);
        }
        this.command_available = sendMessageToOtherPlayerCommand;
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
}


