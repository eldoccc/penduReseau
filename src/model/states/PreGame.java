package model.states;

import model.command.*;

/**
 * This class represents the state of the game when the decider is choosing the word.
 */
public class PreGame extends Etat {

    private BaseCommand DecideWord;  // Command available in this state


    /*
        Constructor of the state PreGame (when the decider is choosing a word)
     */
    public PreGame() {
        super();
        this.DecideWord = new DecideWord(this.quitCommand);
        this.command_available = this.DecideWord;
    }

    @Override
    public String getClientInstruction() {
        return "You preparing the game ! Commands :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "InGame";
    }
}


