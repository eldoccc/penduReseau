package model.command;


public abstract class BaseCommand implements Command {
    protected Command next;
    protected String command;
    protected String[] args;

    public BaseCommand(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public BaseCommand(Command next) {
        this.next = next;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public String getArgsAsString() {
        StringBuilder args = new StringBuilder();
        for (String arg : this.args) {
            args.append(arg).append(" ");
        }
        return args.toString();
    }

    @Override
    public String toString() {
        return "  - " + getUsage() + " : " + getDescription() + " (ex: " + getExample() + ")\n" + (next != null ? next.toString() : "");
    }

    // Abstract method to get the command's help
    public abstract String getUsage();

    // Abstract method to get the command's description
    public abstract String getDescription();

    // Abstract method to get the command's example
    public abstract String getExample();
}
