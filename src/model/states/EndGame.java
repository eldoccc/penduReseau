package model.states;

import model.command.BaseCommand;
import model.command.RestartCommand;
import model.command.ReturnToMenuCommand;
import model.command.SendMessageToOtherPlayerCommand;

/**
 * Class representing the end game state.
 */
public class EndGame extends Etat {

    /*
      Commands available in this state.
     */
    private BaseCommand restartCommand;
    private BaseCommand returnToMenuCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;


    /**
     * Constructor of the class.
     */
    public EndGame() {
        super();
        this.restartCommand = new RestartCommand(this.quitCommand);
        this.returnToMenuCommand = new ReturnToMenuCommand(this.restartCommand);
        this.sendMessageToOtherPlayerCommand = new SendMessageToOtherPlayerCommand(this.returnToMenuCommand);
        this.command_available = sendMessageToOtherPlayerCommand;
    }

    @Override
    public String getClientInstruction() {
        return "You are in the end game ! Commands :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "EndGame";
    }
}
