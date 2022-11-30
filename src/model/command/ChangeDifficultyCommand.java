package model.command;

public class ChangeDifficultyCommand extends BaseCommand {
    private static final String COMMAND = "/difficulty";

    public ChangeDifficultyCommand(String command, String[] args) {
        super(command, args);
    }

    public ChangeDifficultyCommand(Command next) {
        super(next);
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
    public void run() {

    }

    @Override
    public boolean isValid() {
        // Valid if the command is "difficulty" and the difficulty is between 1 and 3
        return this.command.equals(COMMAND) && this.args.length == 1 && Integer.parseInt(this.args[0]) >= 1 && Integer.parseInt(this.args[0]) <= 3;
    }
}
