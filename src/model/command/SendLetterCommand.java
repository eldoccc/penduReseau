package model.command;

public class SendLetterCommand extends BaseCommand{
    public SendLetterCommand(Command next) {
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
