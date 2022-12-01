package model.command;

import model.Response2;
import server.ServerClientThread;

import java.util.ArrayList;

public class AskToPlay extends BaseCommand {
    private static final String COMMAND = "/pw";

    public AskToPlay(String command, String[] args) {
        super(command, args);
    }

    public AskToPlay(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <player_number>";
    }

    @Override
    public String getDescription() {
        return "Choose a player to play with (player_number is the number of the player in the list)";
    }

    @Override
    public String getExample() {
        return COMMAND + " 4";
    }

    @Override
    public Response2 run() {
        this.client.setDifficulty(Integer.parseInt(this.args[0]));
        return new Response2("Difficulty changed to " + this.args[0], this.client.getEtat());
    }

    @Override
    public String isValid() {
        // Check if the number match a player in the queue
        if (this.args.length == 1) {
            try {
                int number = Integer.parseInt(this.args[0]);
                if (number > 0 && number <= this.client.getPlayerInQueue().size()) {
                    return null;
                } else {
                    return "The number must match a player in the queue bellow : \n" + this.client.getPlayerInQueue();
                }
            } catch (NumberFormatException e) {
                return "The number must be an integer";
            }
        } else {
            return "You must enter a number";
        }
    }
}
