package model.command;

import model.Response2;
import model.Wording;
import model.states.InGame;

public class RestartCommand extends BaseCommand {

    private static final String RESTART = "/restart";
    public RestartCommand(BaseCommand nextCommand) {
        super(nextCommand);
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
        Wording wording = Wording.getInstance();
        this.client.changeState(new InGame(this.client.getGame().getTries(),wording.getRandomWord()));
        return new Response2("Restarting the game", this.client.getEtat());
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";
    }
}
