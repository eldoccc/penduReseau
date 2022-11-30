package model.command;

public class SendMessageToOtherPlayerCommand extends BaseCommand{

    private static final String MESSAGE = "/message";

    public SendMessageToOtherPlayerCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return MESSAGE + " <message>";
    }

    @Override
    public String getDescription() {
        return "message the player";
    }

    @Override
    public String getExample() {
        return MESSAGE + "/message hello";
    }


    @Override
    public void run() {

    }

    @Override
    public boolean isValid() {
        return this.command.equals(MESSAGE) && this.args.length == 1 && this.args[0].length() > 0;
    }
}
