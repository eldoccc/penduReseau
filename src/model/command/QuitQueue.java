package model.command;

import model.Response2;

public class QuitQueue extends BaseCommand {
    private static final String COMMAND = "menu";

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
    public Response2 run() {
        this.client.leaveQueue();

        return new Response2("You have left the queue", this.client.getEtat());
    }

    @Override
    public String isValid() {
        return null;
    }
}
