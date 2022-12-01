package model.command;

import model.Response2;
import model.Wording;
import model.game.Game;
import model.game.SingleplayerGame;
import model.states.InGame;

public class RestartCommand extends BaseCommand {

    private static final String RESTART = "restart";
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
    public Response2 run() {
        if (this.client.getGame() instanceof SingleplayerGame) {
            this.client.removeGame(this.client.getGame());
            this.client.setGame(null);

            Wording wording = Wording.getInstance();
            String word = wording.getRandomWord();
            Game g = new SingleplayerGame(word, this.client);
            this.client.addGame(g);
            this.client.setGame(g);
            this.client.setEtat(new InGame(true));

            return new Response2("Restarting a new game\n" + g, this.client.getEtat());
        } else {
            return new Response2("You can't restart a multiplayer game", this.client.getEtat());
        }
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";
    }
}
