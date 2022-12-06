package model.command;

import model.Response;

/**
 * This class is the command to send a message to the general chat (only in the menu)
 */
public class SendMessageToGeneral extends BaseCommand {
    private static final String COMMAND = "msg";  // The command name

    public SendMessageToGeneral(String command, String[] args) {
        super(command, args);
    }

    public SendMessageToGeneral(Command next) {
        super(next);
        this.command_name = COMMAND;
    }


    @Override
    public String getUsage() {
        return COMMAND + " <message>";
    }

    @Override
    public String getDescription() {
        return "Send a message to the general chat in the menu";
    }

    @Override
    public String getExample() {
        return COMMAND + " Hello everyone !";
    }

    @Override
    public Response run() {
        this.client.sendMessageGeneral(this.getArgsAsString());  // Send the message to the general chat
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
