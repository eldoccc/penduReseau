package model.states;

import model.command.BaseCommand;
import model.command.GuessWordCommand;
import model.command.SendLetterCommand;
import model.command.SendMessageToOtherPlayerCommand;

/*
    Class that represents the state of the game when the player is in the game
 */
public class InGame extends Etat {

    /*
    Commands that can be executed in this state
     */
    private BaseCommand sendLetterCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;
    private BaseCommand guessWordCommand;


    /**
     * Constructor of the class according to the role of the player
     */
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
        return "You are in game ! Commandes :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "InGame";
    }
}


