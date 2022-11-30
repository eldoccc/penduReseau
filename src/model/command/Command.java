package model.command;


import java.io.Serializable;

// Main of the chain of responsibility pattern for a command
public interface Command extends Serializable {
    void execute(String command) throws CommandException;
    void run();
    boolean isValid();
}
