package model.game;

import java.net.Socket;

public class SingleplayerGame extends Game{
    public SingleplayerGame(String secretWord, Socket client) {
        super(secretWord, client);
    }
}
