package model.command;

import model.Response2;
import model.game.Game;
import model.game.MultiplayerGame;

public class SendLetterCommand extends BaseCommand{

    private static final String SEND = "try";
    public SendLetterCommand(Command next) {
        super(next);
        this.command_name = SEND;
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
        Game g = this.client.getGame();
        switch (g.playLetter(this.args[0])){
            case ALREADY_PLAYED -> {
                if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The letter " + this.args[0] + " has already been played\n" + g);
                return new Response2("You have already played this letter !\n" + this.client.getGame(), this.client.getEtat());
            }
            case RIGHT -> {
                if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The letter " + this.args[0] + " is in the word\n" + g);
                return new Response2("You have found a letter !\n" + this.client.getGame(), this.client.getEtat());
            }
            case WRONG -> {
                if (g instanceof MultiplayerGame) ((MultiplayerGame) g).getDecider().sendMessage("The letter " + this.args[0] + " is not in the word\n" + g);
                return new Response2("You haven't found a letter !\n" + this.client.getGame(), this.client.getEtat());
            }
        }
        return null;
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