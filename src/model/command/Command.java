package model.command;


import model.Response2;
import server.ServerClientThread;

import java.io.Serializable;

// Main of the chain of responsibility pattern for a command
public interface Command extends Serializable {
    Response2 execute(String command, ServerClientThread c) throws CommandException;
    Response2 run();
    boolean exist();
    String isValid();
}
