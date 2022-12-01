package model.command;

import model.Response2;
import model.game.MultiplayerGame;

public class SendMessageToOtherPlayerCommand extends BaseCommand{

    private static final String MESSAGE = "chat";

    public SendMessageToOtherPlayerCommand(Command next) {
        super(next);
        this.command_name = MESSAGE;
    }

    @Override
    public String getUsage() {
        return MESSAGE + " <message>";
    }

    @Override
    public String getDescription() {
        return "Send a message to the other player";
    }

    @Override
    public String getExample() {
        return MESSAGE + " Oh no, I'm going to lose !";
    }


    @Override
    public Response2 run() {
        // If the player is in a multiplayer game send the message to the other player
        if (this.client.getGame() instanceof MultiplayerGame) {
            MultiplayerGame game = (MultiplayerGame) this.client.getGame();
            if (this.client.isGuesser()) {
                game.getDecider().sendMessage(this.client.getPlayerName() + " : " + this.getArgsAsString());
            } else {
                game.getGuesser().sendMessage(this.client.getPlayerName() + " : " + this.getArgsAsString());
            }
        }
        return null;
    }

    @Override
    public String isValid() {
        // Check if the message is not empty and if the message is not too long else return message
        if (this.args.length >= 1) {
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
