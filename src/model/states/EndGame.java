package model.states;

import model.command.BaseCommand;
import model.command.RestartCommand;
import model.command.ReturnToMenuCommand;
import model.command.SendMessageToOtherPlayerCommand;

public class EndGame extends Etat {

    private BaseCommand restartCommand;
    private BaseCommand returnToMenuCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;

    public EndGame() {
        super();
        this.restartCommand = new RestartCommand(this.quitCommand);
        this.returnToMenuCommand = new ReturnToMenuCommand(this.restartCommand);
        this.sendMessageToOtherPlayerCommand = new SendMessageToOtherPlayerCommand(this.returnToMenuCommand);
        this.command_available = sendMessageToOtherPlayerCommand;
    }

    @Override
    public String getClientInstruction() {
        return "Fin de la partie ! Commandes :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "EndGame";
    }
}
