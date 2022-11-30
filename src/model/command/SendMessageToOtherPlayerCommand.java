package model.command;

import model.Response2;

public class SendMessageToOtherPlayerCommand extends BaseCommand{

    private static final String MESSAGE = "/message";

    public SendMessageToOtherPlayerCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return MESSAGE + " <message>";
    }

    @Override
    public String getDescription() {
        return "message the player";
    }

    @Override
    public String getExample() {
        return MESSAGE + "/message hello";
    }


    @Override
    public Response2 run() {
        return null;
    }

    @Override
    public String isValid() {
        // Check if the message is not empty and if the message is not too long else return message
        if (this.args.length > 0) {
            if (this.getArgsAsString().length() < 100) {
                return null;
            } else {
                return "Message is too long";
            }
        } else {
            return "Message is empty";
        }
    }
}
