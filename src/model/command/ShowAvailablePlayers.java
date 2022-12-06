package model.command;

import model.Response;

/**
 * This class is the command to show the available players in the queue
 */
public class ShowAvailablePlayers extends BaseCommand {
    private static final String COMMAND = "players";  // The command name

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
    public Response run() {
        return new Response("Players available :\n" + this.printPlayers(this.getAvailablePlayers()), this.client.getEtat());  // Print the list of available players
    }

    @Override
    public String isValid() {
        return null;
    }
}
