package model.command;

import model.Response;

/**
 * This class is the command to quit the queue
 */
public class QuitQueue extends BaseCommand {
    private static final String COMMAND = "menu";  // The command name

    public QuitQueue(String command, String[] args) {
        super(command, args);
    }

    public QuitQueue(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND;
    }

    @Override
    public String getDescription() {
        return "Return to the menu and quit the queue";
    }

    @Override
    public String getExample() {
        return COMMAND;
    }

    @Override
    public Response run() {
        this.client.leaveQueue();  // Leave the queue

        return new Response("You have left the queue", this.client.getEtat());
    }

    @Override
    public String isValid() {
        return null;
    }
}
