package model.command;

public class ChangeDifficultyCommand extends BaseCommand {
    private static final String COMMAND = "difficulty";

    public ChangeDifficultyCommand(String command, String[] args) {
        super(command, args);
    }

    public ChangeDifficultyCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return COMMAND + " <difficulty>";
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
    public void execute(String command) throws CommandException {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isValid(String command) {
        return COMMAND.equals(command);
    }
}
