package model.command;


import model.Response;
import server.ServerClientThread;

import java.io.Serializable;

// Main of the chain of responsibility pattern for a command
public interface Command extends Serializable {
    // Execute the command and return the response to send to the client (it check if the command is valid)
    Response execute(String command, ServerClientThread c) throws CommandException;
    Response run();  // Run the command
    boolean exist();  // Check if the command exist
    String isValid();  // Check if the command is valid
}
