package model.states;

import model.command.*;

public class PreGame extends Etat {

    private BaseCommand DecideWord;


    public PreGame() {
        super();
        this.DecideWord = new DecideWord(this.quitCommand);
        this.command_available = this.DecideWord;
    }

    @Override
    public String getClientInstruction() {
        return "You preparing the game ! Commandes :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "InGame";
    }
}


