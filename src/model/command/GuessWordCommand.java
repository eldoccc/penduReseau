package model.command;

import model.Response2;
import model.game.Game;
import model.game.MultiplayerGame;

public class GuessWordCommand extends BaseCommand{

    private static final String COMMAND = "guess";

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
    public Response2 run() {
        Game g = this.client.getGame();
        if(g.guessWord(this.args[0])){
            if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The guesser guessed the word !\n" + g);
            return new Response2("You have guessed the word !", this.client.getEtat());
        }
        else {
            if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The guesser tried the word " + this.args[0] + "\n" + g);
            return new Response2("You haven't managed to guess the word !", this.client.getEtat());
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
