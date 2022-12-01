package model.states;

import model.command.*;

public class Menu extends Etat {

    private BaseCommand changeDifficultyCommand, sendMessageGeneral, playCommand, pvpCommand;

    public Menu() {
        super();
        this.sendMessageGeneral = new SendMessageToGeneral(this.quitCommand);
        this.changeDifficultyCommand = new ChangeDifficultyCommand(this.sendMessageGeneral);
        this.playCommand = new PlayCommand(this.changeDifficultyCommand);
        this.pvpCommand = new PlayVsPlayer(this.playCommand);
        this.command_available = pvpCommand;
    }

    @Override
    public String getClientInstruction() {
        return "Menu of the hangman game ! Commands :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "Menu";
    }
}
