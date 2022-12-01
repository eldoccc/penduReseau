package model.command;

import model.Response2;
import model.Wording;
import model.game.Game;
import model.game.SingleplayerGame;
import model.states.InGame;

public class PlayCommand extends BaseCommand{

    private static final String PLAY = "play";

    public PlayCommand(BaseCommand command) {
        super(command);
        this.command_name = PLAY;
    }

    @Override
    public String getUsage() {
        return PLAY;
    }

    @Override
    public String getDescription() {
        return "Play a game";
    }

    @Override
    public String getExample() {
        return PLAY;
    }

    @Override
    public Response2 run() {
        Wording wording = Wording.getInstance();
        String word = wording.getRandomWord();
        Game g = new SingleplayerGame(word, this.client);
        this.client.addGame(g);
        this.client.setGame(g);
        this.client.leaveMenu();
        this.client.setEtat(new InGame(true));

        return new Response2("Starting a new game\n" + g, this.client.getEtat());
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";
    }
}
