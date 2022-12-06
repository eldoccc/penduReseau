package model.states;

import model.command.*;

/*
    Class Menu : state of the game when the player is in the menu
 */
public class Menu extends Etat {

    private BaseCommand changeDifficultyCommand, sendMessageGeneral, playCommand, pvpCommand;  // Cammands available in the menu

    /**
     * Constructor of the Menu state
     */
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
