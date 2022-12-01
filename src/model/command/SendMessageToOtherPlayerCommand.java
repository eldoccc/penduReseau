package model.command;

import model.Response2;

public class SendMessageToOtherPlayerCommand extends BaseCommand{

    private static final String MESSAGE = "/dm";

    public SendMessageToOtherPlayerCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return MESSAGE + " <direct_message>";
    }

    @Override
    public String getDescription() {
        return "dm the player";
    }

    @Override
    public String getExample() {
        return MESSAGE + " hello";
    }


    @Override
    public Response2 run() {
        this.client.sendMessageToOtherPlayer(this.args[0],this.args[1]);
        return null;
    }

    @Override
    public String isValid() {
        // Check if the message is not empty and if the message is not too long else return message
        if (this.args.length > 1) {
            if (this.getArgsAsString().length() < 100) {
                return null;
            } else {
                return "dm is too long";
            }
        } else {
            return "dm is empty";
        }
    }
}
