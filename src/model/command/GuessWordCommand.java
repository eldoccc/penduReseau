package model.command;

public class GuessWordCommand extends BaseCommand{
    public GuessWordCommand(Command next) {
        super(next);
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
