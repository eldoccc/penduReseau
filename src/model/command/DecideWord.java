package model.command;

import model.Response2;
import model.game.Game;
import model.game.MultiplayerGame;
import model.states.InGame;
import server.ServerClientThread;

public class DecideWord extends BaseCommand {
    private static final String COMMAND = "decide";

    public DecideWord(String command, String[] args) {
        super(command, args);
    }

    public DecideWord(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <word>";
    }

    @Override
    public String getDescription() {
        return "Decide the word to guess";
    }

    @Override
    public String getExample() {
        return COMMAND + " Réseau";
    }

    @Override
    public Response2 run() {
        ServerClientThread guesser = this.client.getPlayerAsked();
        if (guesser != null) {
            this.client.setPlayerAsked(null);
            guesser.setPlayerAsked(null);
            Game g = new MultiplayerGame(this.args[0], guesser, this.client);
            guesser.setGame(g);
            this.client.setGame(g);

            this.client.leaveQueue();
            guesser.leaveQueue();

            guesser.sendMessage("The decider has chosen a word, you can start playing !");

            this.client.setEtat(new InGame(false));
            this.client.sendMessage("You have chosen a word, you can start playing !");
            guesser.setEtat(new InGame(true));
            guesser.sendMessage("You can start playing !\n" + g);
            return new Response2("You have accepted the invitation to play", this.client.getEtat());
        }

        return null;
    }

    @Override
    public String isValid() {
        // Check if the word is valid
        if (this.args.length == 0) {
            return "You must enter a word";
        }
        if (this.args.length > 1) {
            return "You must enter only one word";
        }
        return null;
    }
}
