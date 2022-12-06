package model.command;

import model.Response;
import model.states.PreGame;
import server.ServerClientThread;

/**
 * This class is the command to accept to play with another player
 */
public class AcceptToPlay extends BaseCommand {
    private static final String COMMAND = "accept";  // The command name

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
    public Response run() {
        ServerClientThread playerAsked = this.client.getPlayerAsked();
        ServerClientThread guesser;
        ServerClientThread decider;
        if (playerAsked != null) {  // If the player has been invited
            if (this.client.isGuesser()) {  // If the player is the guesser
                guesser = this.client;
                decider = playerAsked;
            } else {  // If the player is the decider
                guesser = playerAsked;
                decider = this.client;
            }
            decider.setEtat(new PreGame());  // Set the state of the decider to PreGame
            decider.sendMessage("You need to decide a word to guess");  // Send a message to the decider
            guesser.sendMessage("The decider is choosing a word, please wait...");  // Send a message to the guesser


            return new Response("You accept the invitation of " + this.client.getPlayerAsked().getPlayerName(), this.client.getEtat());  // Send a response to the player
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
