package model.command;

import model.Response2;

public class ShowAvailablePlayers extends BaseCommand {
    private static final String COMMAND = "players";

    public ShowAvailablePlayers(String command, String[] args) {
        super(command, args);
    }

    public ShowAvailablePlayers(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND;
    }

    @Override
    public String getDescription() {
        return "Show the list of available players";
    }

    @Override
    public String getExample() {
        return COMMAND;
    }

    @Override
    public Response2 run() {
        return new Response2("Players available :\n" + this.printPlayers(this.getAvailablePlayers()), this.client.getEtat());
    }

    @Override
    public String isValid() {
        return null;
    }
}
