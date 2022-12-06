package model.command;

import model.Response;
import model.Wording;
import model.game.Game;
import model.game.SingleplayerGame;
import model.states.InGame;

/**
 * This class is the command that plays a single player game
 */
public class PlayCommand extends BaseCommand{

    private static final String PLAY = "play";  // The command name

    public PlayCommand(BaseCommand command) {
        super(command);
        this.command_name = PLAY;
    }

    @Override
    public String getUsage() {
        return PLAY;
    }

    @Override
    public String getDescription() {
        return "Play a game";
    }

    @Override
    public String getExample() {
        return PLAY;
    }


    @Override
    public Response run() {
        Wording wording = Wording.getInstance();
        String word = wording.getRandomWord();  // Get a random word
        Game g = new SingleplayerGame(word, this.client);  // Create a new single player game
        this.client.addGame(g);
        this.client.setGame(g);
        this.client.leaveMenu();
        this.client.setEtat(new InGame(true));

        return new Response("Starting a new game\n" + g, this.client.getEtat());
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";  // Valid if there is no arguments
    }
}
