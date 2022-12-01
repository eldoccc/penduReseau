package model.command;

import model.Response2;
import model.game.MultiplayerGame;
import model.states.PreGame;
import server.ServerClientThread;

public class AcceptToPlay extends BaseCommand {
    private static final String COMMAND = "accept";

    public AcceptToPlay(String command, String[] args) {
        super(command, args);
    }

    public AcceptToPlay(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND;
    }

    @Override
    public String getDescription() {
        return "Accept the invitation to play if a player has invited you";
    }

    @Override
    public String getExample() {
        return COMMAND;
    }

    @Override
    public Response2 run() {
        ServerClientThread playerAsked = this.client.getPlayerAsked();
        ServerClientThread guesser;
        ServerClientThread decider;
        if (playerAsked != null) {
            if (this.client.isGuesser()) {
                guesser = this.client;
                decider = playerAsked;
            } else {
                guesser = playerAsked;
                decider = this.client;
            }
            decider.setEtat(new PreGame());
            decider.sendMessage("You need to decide a word to guess");
            guesser.sendMessage("The decider is choosing a word, please wait...");


            return new Response2("You accept the invitation of " + this.client.getPlayerAsked().getPlayerName(), this.client.getEtat());
        }

        return null;
    }

    @Override
    public String isValid() {
        // If the player has been invited to play
        if (this.client.getPlayerAsked() == null) {
            return "You have not been invited to play";
        }
        return null;
    }
}
