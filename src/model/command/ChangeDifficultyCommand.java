package model.command;

import model.Response2;

public class ChangeDifficultyCommand extends BaseCommand {
    private static final String COMMAND = "/difficulty";

    public ChangeDifficultyCommand(String command, String[] args) {
        super(command, args);
    }

    public ChangeDifficultyCommand(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <difficulty number>";
    }

    @Override
    public String getDescription() {
        return "Change the difficulty of the game (easy, medium, hard = 1, 2, 3)";
    }

    @Override
    public String getExample() {
        return COMMAND + " 2";
    }

    @Override
    public Response2 run() {
        this.client.setDifficulty(Integer.parseInt(this.args[0]));
        return new Response2("Difficulty changed to " + this.args[0], this.client.getEtat());
    }

    @Override
    public String isValid() {
        // Valid if the difficulty is between 1 and 3 else return an error message
        if (this.args.length == 1 && this.args[0].length() == 1) {
            int difficulty = Integer.parseInt(this.args[0]);
            if (difficulty >= 1 && difficulty <= 3) {
                return null;
            }
            return "The difficulty must be between 1 and 3";
        }
        return "The difficulty must be a number between 1 and 3 (usage :" + this.getUsage() + ")";
    }
}
