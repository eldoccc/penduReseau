package model.command;

import model.Response2;
import model.states.Etat;

import java.io.IOException;

public class QuitCommand extends BaseCommand {
    private static final String COMMAND = "quit";
    public static final String QUIT_COMMAND_PUBLIC = COMMAND;

    public QuitCommand(String command, String[] args) {
        super(command, args);
    }

    public QuitCommand(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return "quit";
    }

    @Override
    public String getDescription() {
        return "Quit the game";
    }

    @Override
    public String getExample() {
        return COMMAND;
    }

    @Override
    public Response2 run() {
        // Close all necessary things
        try {
            this.client.end();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Response2("You have quit the game", this.client.getEtat());
    }


    @Override
    public String isValid() {
        // Check if there is no argument
        if (this.args.length == 0) {
            return null;
        } else {
            return "Too many arguments";
        }
    }
}
