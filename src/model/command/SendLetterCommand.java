package model.command;

import model.Response2;

public class SendLetterCommand extends BaseCommand{

    private static final String SEND = "/try";
    public SendLetterCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return SEND + " <letter>";
    }

    @Override
    public String getDescription() {
        return "Send a letter to the server, this is a default command, you can use it without typing /try";
    }

    @Override
    public String getExample() {
        return SEND + " a";
    }

    @Override
    public Response2 run() {
        return new Response2("You have sent the letter " + this.args[0], this.client.getEtat());
    }

    @Override
    public String isValid() {
        // Check if the letter is not empty and if the argument is a letter else return message
        if (this.args.length > 0) {
            if (this.args[0].length() == 1) {
                if (Character.isLetter(this.args[0].charAt(0))) {
                    return null;
                } else {
                    return "Argument is not a letter";
                }
            } else {
                return "Argument is not a letter";
            }
        } else {
            return "Argument is empty";
        }
    }
}

