package model.game;

import server.ServerClientThread;

import java.net.Socket;

/**
 * Multiplayer game class represent a game with two players (the decider and the player)
 */
public class MultiplayerGame extends Game{
    private ServerClientThread joueur2;  // The second player (the decider)


    /**
     * Constructor of the multiplayer game class with the secret word, the number of tries and the duration of the game
     *
     * @param secretWord The secret word of the game
     * @param guesser   The guesser of the game
     * @param decider  The decider of the game
     */
    public MultiplayerGame(String secretWord, ServerClientThread guesser, ServerClientThread decider) {
        super(secretWord, guesser);
        this.joueur2 = decider;
    }

    public ServerClientThread getGuesser() {
        return this.getJoueur1();
    }

    public ServerClientThread getDecider() {
        return this.joueur2;
    }
}
