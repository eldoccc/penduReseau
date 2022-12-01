package model.states;

import model.command.*;
import model.command.BaseCommand;
import model.command.ChangeDifficultyCommand;
import model.command.SendMessageToGeneral;

public class InQueue extends Etat {

    private BaseCommand AskToPlay, AcceptToPlay, DeclineToPlay, QuitQueue, ShowPlayers;
    public InQueue() {
        super();
        this.AskToPlay = new AskToPlay(this.quitCommand);
        this.AcceptToPlay = new AcceptToPlay(this.AskToPlay);
        this.DeclineToPlay = new DeclineToPlay(this.AcceptToPlay);
        this.QuitQueue = new QuitQueue(this.DeclineToPlay);
        this.ShowPlayers = new ShowAvailablePlayers(this.QuitQueue);
        this.command_available = this.ShowPlayers;
    }

    @Override
    public String getClientInstruction() {
        return "You enter in search of an opponent... Commands :\n" +
                this.command_available.toString();
    }

    @Override
    public String toString() {
        return "Menu";
    }
}
