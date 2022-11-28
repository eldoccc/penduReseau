package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Interlocuteur extends Thread {

    BufferedReader fluxEntrant;
    PrintStream fluxSortant;
    int noClient;

    public Interlocuteur(Socket socket, int noClient) throws IOException {
        this.fluxEntrant = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.fluxSortant = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                String requete = fluxEntrant.readLine();
                System.out.println("Le client n°" + this.noClient + " a envoyé : " + requete);
                String reponse = requete.toUpperCase();
                this.fluxSortant.println(reponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
