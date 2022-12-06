package model.command;

import model.Response;
import model.Wording;
import model.game.Game;
import model.game.SingleplayerGame;
import model.states.InGame;

/**
 * This class is the command that restart a single player game (multiplayer is not implemented)
 */
public class RestartCommand extends BaseCommand {

    private static final String RESTART = "restart";  // The command name
    public RestartCommand(BaseCommand nextCommand) {
        super(nextCommand);
        this.command_name = RESTART;
    }

    @Override
    public String getUsage() {
        return RESTART;
    }

    @Override
    public String getDescription() {
        return "Restart the game";
    }

    @Override
    public String getExample() {
        return RESTART;
    }

    @Override
    public Response run() {
        if (this.client.getGame() instanceof SingleplayerGame) {  // If the game is a single player game
            this.client.removeGame(this.client.getGame());  // Remove the previous game
            this.client.setGame(null);

            // Create a new game
            Wording wording = Wording.getInstance();
            String word = wording.getRandomWord();
            Game g = new SingleplayerGame(word, this.client);
            this.client.addGame(g);
            this.client.setGame(g);
            this.client.setEtat(new InGame(true));

            return new Response("Restarting a new game\n" + g, this.client.getEtat());
        } else {
            return new Response("You can't restart a multiplayer game", this.client.getEtat());
        }
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";  // Valid if there is no arguments
    }
}
