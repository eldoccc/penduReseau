package model.command;

/*
    * This class is the exception of the command pattern
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}

