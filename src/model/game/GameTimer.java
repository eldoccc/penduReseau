package model.game;

import model.Response;
import model.states.EndGame;

import java.util.TimerTask;

/**
 * This class is the task of the timer of the game
 */
public class GameTimer extends TimerTask {

    private Game game;  // The game

    /*
        * Constructor of the game timer
     */
    public GameTimer(Game g) {
        this.game = g;
    }

    /**
     * This method is called when the timer is finished and the game is over (the player has lost but the opponent has won if there is one)
     */
    public void run() {
        this.game.getJoueur1().setEtat(new EndGame());
        this.game.getJoueur1().sendResponse(new Response("You lost, the timer is over. The word was " + this.game.getSecretWord(), this.game.getJoueur1().getEtat()));

        if (this.game instanceof MultiplayerGame) {
            MultiplayerGame g = (MultiplayerGame) this.game;
            g.getDecider().setEtat(new EndGame());
            g.getDecider().sendResponse(new Response("You won, the timer is over.", g.getDecider().getEtat()));
        }
    }
}
