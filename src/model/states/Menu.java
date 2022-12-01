package model.states;

import model.command.BaseCommand;
import model.command.ChangeDifficultyCommand;
import model.command.PlayCommand;
import model.command.SendMessageToGeneral;

public class Menu extends Etat {

    private BaseCommand changeDifficultyCommand, sendMessageGeneral,playCommand;

    public Menu() {
        super();
        this.sendMessageGeneral = new SendMessageToGeneral(this.quitCommand);
        this.changeDifficultyCommand = new ChangeDifficultyCommand(this.sendMessageGeneral);
        this.playCommand = new PlayCommand(this.changeDifficultyCommand);
        this.command_available = playCommand;
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
