package model.command;

public class RestartCommand extends BaseCommand {
    public RestartCommand(BaseCommand nextCommand) {
        super(nextCommand);
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getExample() {
        return null;
    }

    @Override
    public void execute(String command) throws CommandException {

    }

    @Override
    public boolean isValid(String command) {
        return false;
    }
}
