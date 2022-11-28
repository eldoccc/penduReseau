package model.states;

import model.command.BaseCommand;
import model.command.Command;
import model.command.QuitCommand;

import java.io.Serializable;

public abstract class Etat implements Serializable {
    protected Command command_available;
    protected BaseCommand quitCommand;

    public Etat() {
        this.quitCommand = new QuitCommand((BaseCommand) null);
    }

    abstract public String getClientInstruction();
}
