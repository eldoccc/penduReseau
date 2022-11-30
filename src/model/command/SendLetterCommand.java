package model.command;

public class SendLetterCommand extends BaseCommand{

    private static final String SEND = "/try";
    public SendLetterCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return SEND + " <letter>";
    }

    @Override
    public String getDescription() {
        return "Send a letter to the server, this is a default command, you can use it without typing /try";
    }

    @Override
    public String getExample() {
        return SEND + " a";
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isValid() {
        return this.command.equals(SEND) && this.args.length == 1 && this.args[0].length() == 1;
    }
}

