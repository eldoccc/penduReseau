package model.game;

import model.Response2;
import model.states.EndGame;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends TimerTask {

    private Game game;

    public GameTimer(Game g) {
        this.game = g;
    }

    public void run() {
        this.game.getJoueur1().setEtat(new EndGame());
        this.game.getJoueur1().sendResponse(new Response2("You lost, the timer is over. The word was " + this.game.getSecretWord(), this.game.getJoueur1().getEtat()));

        if (this.game instanceof MultiplayerGame) {
            MultiplayerGame g = (MultiplayerGame) this.game;
            g.getDecider().setEtat(new EndGame());
            g.getDecider().sendResponse(new Response2("You won, the timer is over.", g.getDecider().getEtat()));
        }
    }
}
