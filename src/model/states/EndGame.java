package model.states;

import model.command.BaseCommand;

public class EndGame extends Etat {

    private BaseCommand restartCommand;
    private BaseCommand returnToMenuCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;

    public EndGame() {
        super();
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
