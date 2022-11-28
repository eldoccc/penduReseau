package model.states;

import model.command.BaseCommand;
import model.command.ChangeDifficultyCommand;

public class Menu extends Etat {

    private BaseCommand changeDifficultyCommand;
    public Menu() {
        super();
        this.changeDifficultyCommand = new ChangeDifficultyCommand(this.quitCommand);
        this.command_available = changeDifficultyCommand;
    }

    @Override
    public String getClientInstruction() {
        return "Menu du jeu du pendu ! Commandes :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "Menu";
    }
}
