package model.command;

import model.Response;
import model.game.Game;
import model.game.MultiplayerGame;

/**
 * This class is the command that guesses the word in a single try
 */
public class GuessWordCommand extends BaseCommand{

    private static final String COMMAND = "guess";  // The command name

    public GuessWordCommand(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <word>";
    }

    @Override
    public String getDescription() {
        return "Guess the word";
    }

    @Override
    public String getExample() {
        return COMMAND + " telephone";
    }

    @Override
    public Response run() {
        Game g = this.client.getGame();  // Get the game of the player
        if(g.guessWord(this.args[0])){  // If the word is guessed then send a confirmation to all players
            if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The guesser guessed the word !\n" + g);
            return new Response("You have guessed the word !", this.client.getEtat());
        }
        else {  // Else send that the word is not guessed
            if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The guesser tried the word " + this.args[0] + "\n" + g);
            return new Response("You haven't managed to guess the word !", this.client.getEtat());
        }
    }

    @Override
    public String isValid() {
        // Check if the word is not empty and if the word is not too long else return message
        if (this.args.length > 0) {
            if (this.getArgsAsString().length() < 100) {
                return null;
            } else {
                return "Word is too long";
            }
        } else {
            return "Word is empty";
        }
    }

}
