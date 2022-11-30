package model.command;


import model.Response2;
import model.states.Etat;
import server.ServerClientThread;

public abstract class BaseCommand implements Command {


    protected ServerClientThread client;
    protected Command next;
    protected String command;
    protected String[] args;
    protected String command_name;

    public BaseCommand(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public BaseCommand(Command next) {
        this.next = next;
    }

    // Convert all arguments as a single string
    public String getArgsAsString() {
        StringBuilder args = new StringBuilder();
        for (String arg : this.args) {
            args.append(arg).append(" ");
        }
        return args.toString();
    }

    // Convert the command string into the command arguments
    public void parse(String command) {
        String[] commandParts = command.split(" ");
        this.command = commandParts[0];
        this.args = new String[commandParts.length - 1];
        System.arraycopy(commandParts, 1, this.args, 0, commandParts.length - 1);
    }


    // Execute the command
    public Response2 execute(String command, ServerClientThread c) throws CommandException {
        try {
            this.parse(command);
            this.client = c;
        } catch(Exception e) {
            throw new CommandException("Erreur de synthaxe : " + e.getMessage());
        }

        if (exist()) {
            String tmp = isValid();
            if (tmp != null) {
                throw new CommandException(tmp);
            }
            return run();
        } else if (next != null) {
            return next.execute(command, c);
        } else {
            throw new CommandException("Command not found");
        }
    }

    // Check if the command exist
    public boolean exist() {
        return this.command.equals(this.command_name);
    }

    // Abstract method to get the command's help
    public abstract String getUsage();

    // Abstract method to get the command's description
    public abstract String getDescription();

    // Abstract method to get the command's example
    public abstract String getExample();



    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "  - " + getUsage() + " : " + getDescription() + " (ex: " + getExample() + ")\n" + (next != null ? next.toString() : "");
    }
}
