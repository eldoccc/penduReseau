package model.game;

import java.net.Socket;

public class MultiplayerGame extends Game{
    private String lastMessageSent;
    private Socket joueur2;


    public MultiplayerGame(String secretWord, Socket client) {
        super(secretWord, client);
    }

    public String getLastMessageSent() {
        return lastMessageSent;
    }

    public void setLastMessageSent(String lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }

    public Socket getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Socket joueur2) {
        this.joueur2 = joueur2;
    }


    @Override
    public String toString() {
        return "MultiplayerGame{" +
                "joueur2=" + joueur2 +
                '}';
    }
}
