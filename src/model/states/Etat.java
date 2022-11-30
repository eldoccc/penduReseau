package model.states;

import model.Response2;
import model.command.BaseCommand;
import model.command.Command;
import model.command.CommandException;
import model.command.QuitCommand;
import server.ServerClientThread;

import java.io.Serializable;

public abstract class Etat implements Serializable {
    protected Command command_available;
    protected BaseCommand quitCommand;

    public Etat() {
        this.quitCommand = new QuitCommand((BaseCommand) null);
    }

    // Execute the command
    public Response2 execute(String command, ServerClientThread c) throws CommandException {
        return this.command_available.execute(command, c);
    }

    abstract public String getClientInstruction();
}
