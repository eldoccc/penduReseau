package model.command;

public class SendMessageToOtherPlayerCommand extends BaseCommand{
    public SendMessageToOtherPlayerCommand(Command next) {
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