package model.command;

import model.Response2;
import server.ServerClientThread;

public class PlayVsPlayer extends BaseCommand {
    private static final String COMMAND = "pvp";

    public PlayVsPlayer(String command, String[] args) {
        super(command, args);
    }

    public PlayVsPlayer(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <isGuesser> (true/false)";
    }

    @Override
    public String getDescription() {
        return "Join the queue to play with another player (isGuesser is a boolean to choose if you want to be the guesser or the master)";
    }

    @Override
    public String getExample() {
        return COMMAND + " false";
    }

    @Override
    public Response2 run() {
        this.client.setGuesser(Boolean.parseBoolean(this.args[0]));
        this.client.joinQueue();

        return new Response2(("You joined the queue" + "\n" + "Players available :\n" + this.printPlayers(this.getAvailablePlayers())), this.client.getEtat());
    }

    @Override
    public String isValid() {
        // Check if the argument is a boolean
        if (this.args.length != 1) {
            return "Invalid number of arguments";
        }
        if (!this.args[0].equals("true") && !this.args[0].equals("false")) {
            return "Invalid argument";
        }
        return null;
    }
}
