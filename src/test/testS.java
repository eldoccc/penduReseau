package src.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class testS {

    public static void main(String[] args) {
        try {
            int portServer = 9111;
            ServerSocket serveur = new ServerSocket(portServer);

            System.out.println("Serveur de majuscule créé "+serveur);
            int noClient = 0;
            while (true){
                Socket socket = serveur.accept();
                System.out.println("Connexion réussie avec le client n°"+noClient);

                Interlocuteur interlocuteur = new Interlocuteur(socket, noClient);
                noClient++;
                interlocuteur.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
