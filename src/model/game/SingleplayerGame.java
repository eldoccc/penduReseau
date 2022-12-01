package model.game;

import server.ServerClientThread;

import java.net.Socket;

public class SingleplayerGame extends Game{
    public SingleplayerGame(String secretWord, ServerClientThread client) {
        super(secretWord, client);
    }
}
