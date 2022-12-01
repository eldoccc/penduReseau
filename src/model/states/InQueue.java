package model.states;

import model.command.BaseCommand;
import model.command.ChangeDifficultyCommand;
import model.command.SendMessageToGeneral;

public class InQueue extends Etat {

    private boolean foundTheWord;  // True if the player will find the word false if he give the word
    //private BaseCommand changeDifficultyCommand, sendMessageGeneral;
    public InQueue(boolean f) {
        super();
        this.foundTheWord = f;
        this.command_available = this.quitCommand;
    }

    @Override
    public String getClientInstruction() {
        return "Vous entrez en recherche de partie... Commandes :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "Menu";
    }
}
