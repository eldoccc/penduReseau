package model.command;

public class QuitCommand extends BaseCommand {
    private static final String COMMAND = "quit";
    public static final String QUIT_COMMAND_PUBLIC = COMMAND;

    public QuitCommand(String command, String[] args) {
        super(command, args);
    }

    public QuitCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return "quit";
    }

    @Override
    public String getDescription() {
        return "Quit the game";
    }

    @Override
    public String getExample() {
        return COMMAND;
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
