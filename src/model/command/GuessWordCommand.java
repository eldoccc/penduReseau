package model.command;

import model.Response2;

public class GuessWordCommand extends BaseCommand{

    private static final String COMMAND = "/guess";

    public GuessWordCommand(Command next) {
        super(next);
    }

    @Override
    public String getUsage() {
        return COMMAND + " <word>";
    }

    @Override
    public String getDescription() {
        return "Guess the word";
    }

    @Override
    public String getExample() {
        return COMMAND + " telephone";
    }

    @Override
    public Response2 run() {
        return new Response2("You have guessed the word", this.client.getEtat());
    }

    @Override
    public String isValid() {
        // Check if the word is not empty and if the word is not too long else return message
        if (this.args.length > 0) {
            if (this.getArgsAsString().length() < 100) {
                return null;
            } else {
                return "Word is too long";
            }
        } else {
            return "Word is empty";
        }
    }

}
