package model.command;


import model.Response;
import server.ServerClientThread;

import java.util.ArrayList;

/**
 * This class is the base command class of the command pattern (it's a model of all the commands)
 */
public abstract class BaseCommand implements Command {


    protected ServerClientThread client;  // The client that sent the command
    protected Command next;  // The next command (to transfer the command to the next handler)
    protected String command;  // The command
    protected String[] args;  // The arguments of the command
    protected String command_name;  // The name of the command (first argument of the command)

    /**
     * Constructor of the base command class (never used)
     * @param command The command
     * @param args The arguments of the command
     */
    public BaseCommand(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    /**
     * Constructor of the base command class
     * @param next The next command (to transfer the command to the next handler)
     */
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

    // Get all available players (for play with)
    public ArrayList<ServerClientThread> getAvailablePlayers() {
        // Player is available if he is in the queue, if his isGuesser is different and if he is not in playerAsked
        return this.client.getPlayerInQueue().stream().filter(player -> player.isGuesser() != this.client.isGuesser() && player.getPlayerAsked() == null).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Methods to print all the available players to play with
    public String printPlayers(ArrayList<ServerClientThread> players) {
        StringBuilder playersString = new StringBuilder();
        for (ServerClientThread sct : players) {
            playersString.append(sct.toString()).append("\n");
        }
        return playersString.toString();
    }

    // Convert the command string into the command arguments
    public void parse(String command) {
        String[] commandParts = command.split(" ");
        this.command = commandParts[0];
        this.args = new String[commandParts.length - 1];
        System.arraycopy(commandParts, 1, this.args, 0, commandParts.length - 1);
    }


    // Execute the command with all verification and return the response
    public Response execute(String command, ServerClientThread c) throws CommandException {
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
