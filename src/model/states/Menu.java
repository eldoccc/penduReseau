package model.states;

import model.command.BaseCommand;
import model.command.ChangeDifficultyCommand;
import model.command.SendMessageToGeneral;

public class Menu extends Etat {

    private BaseCommand changeDifficultyCommand, sendMessageGeneral;
    public Menu() {
        super();
        this.sendMessageGeneral = new SendMessageToGeneral(this.quitCommand);
        this.changeDifficultyCommand = new ChangeDifficultyCommand(this.sendMessageGeneral);
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
