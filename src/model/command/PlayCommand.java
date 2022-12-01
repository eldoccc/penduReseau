package model.command;

import model.Response2;
import model.Wording;
import model.states.InGame;

public class PlayCommand extends BaseCommand{

    private static final String PLAY = "/play";

    public PlayCommand(BaseCommand command) {
        super(command);
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
    public Response2 run() {
        Wording wording = Wording.getInstance();
        this.client.changeState(new InGame(this.client.getGame().getTries(),wording.getRandomWord()));
        return new Response2("Starting a new game", this.client.getEtat());
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";
    }
}
