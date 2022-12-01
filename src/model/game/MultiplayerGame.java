package model.game;

import server.ServerClientThread;

import java.net.Socket;

public class MultiplayerGame extends Game{
    private String lastMessageSent;
    private ServerClientThread joueur2;


    public MultiplayerGame(String secretWord, ServerClientThread guesser, ServerClientThread decider) {
        super(secretWord, guesser);
        this.joueur2 = decider;
    }

    public String getLastMessageSent() {
        return lastMessageSent;
    }

    public void setLastMessageSent(String lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }

    public ServerClientThread getGuesser() {
        return this.getJoueur1();
    }

    public ServerClientThread getDecider() {
        return this.joueur2;
    }
}
