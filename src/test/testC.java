package src.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;

public class testC {

    public static void main(String[] args){

        try {
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Tapez l'adresse IP du server de majuscule :\n");

            String sAdresseServer = clavier.readLine();

            System.out.println("\nTapez le port du server de majuscule :\n");

            int portServer = Integer.parseInt(clavier.readLine().trim());

            InetAddress adresseServeur = InetAddress.getByName(sAdresseServer);

            Socket socket = new Socket(adresseServeur, portServer);

            System.out.println("Socket créée = "+ socket);

            BufferedReader fluxEntrant = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream fluxSortant = new PrintStream(socket.getOutputStream());

            boolean ok = true;
            do {
                System.out.println("Tapez la chaine à envoyer au serveur ou quittez la conversation avec \"quitter\" \n");
                String requete = clavier.readLine().trim();
                if(requete.equalsIgnoreCase("quitter")){
                    ok = false;
                }
                else{
                    fluxSortant.println(requete);
                    String reponse = fluxEntrant.readLine();
                    System.out.println("\nRéponse serveur : \n"+reponse);
                }
            }
            while(ok);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
