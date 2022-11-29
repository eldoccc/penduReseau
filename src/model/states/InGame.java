package model.states;

import model.command.BaseCommand;

public class InGame extends Etat {

    private BaseCommand sendLetterCommand;
    private BaseCommand sendMessageToOtherPlayerCommand;
    private BaseCommand guessWordCommand;

    public InGame() {
        super();
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
