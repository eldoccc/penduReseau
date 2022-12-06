package model.states;

import model.Response;
import model.command.BaseCommand;
import model.command.Command;
import model.command.CommandException;
import model.command.QuitCommand;
import server.ServerClientThread;

import java.io.Serializable;

/**
 * Abstract class representing a state of the player in the application
 */
public abstract class Etat implements Serializable {
    protected Command command_available;  // command available in this state with the chain of responsibility pattern
    protected BaseCommand quitCommand;  // quit command (available in every state)

    /**
     * Constructor of the class
     */
    public Etat() {
        this.quitCommand = new QuitCommand((BaseCommand) null);
    }

    // Execute the command and apply the changes to the player (if no command is found, throw an CommandException)
    public Response execute(String command, ServerClientThread c) throws CommandException {
        return this.command_available.execute(command, c);
    }

    // Get a string representing the state with all command available
    abstract public String getClientInstruction();
}
