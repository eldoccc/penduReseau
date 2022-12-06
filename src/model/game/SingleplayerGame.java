package model.game;

import server.ServerClientThread;

import java.net.Socket;

/**
 * Single player game class represent a game with one player
 */
public class SingleplayerGame extends Game{
    public SingleplayerGame(String secretWord, ServerClientThread client) {
        super(secretWord, client);
    }
}
