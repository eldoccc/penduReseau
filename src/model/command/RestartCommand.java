package model.command;

import model.Response2;

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
        return new Response2("Restarting the game", this.client.getEtat());
    }

    @Override
    public boolean isValid() {
        return this.command.equals(RESTART);
    }
}
