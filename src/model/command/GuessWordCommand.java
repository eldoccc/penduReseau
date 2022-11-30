package model.command;

public class GuessWordCommand extends BaseCommand{

    private static final String COMMAND = "/guess";

    public GuessWordCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return COMMAND + " <word>";
    }

    @Override
    public String getDescription() {
        return "Guess the word";
    }

    @Override
    public String getExample() {
        return COMMAND + " telephone";
    }

    @Override
    public void execute(String command) throws CommandException {

    }

    @Override
    public void run() {

    }

    @Override
    public boolean isValid() {
        return this.command.equals(COMMAND) && this.args.length == 1 && this.args[0].length() > 1;
    }

}
