package client;

import model.Response;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String requete = "";
        Response response = null;
        System.out.println("Salut à toi JOUEUR1, bienvenue sur le jeu du pendu ! \n");
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket("192.168.169.205", 1234);
        //BufferedReader inFlux = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ObjectInputStream inFlux = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        PrintStream outFlux = new PrintStream(socket.getOutputStream());

        boolean quit = false;
        do {
            System.out.println("Tapes la lettre à envoyer au serveur ou quittes la conversation avec \"quitter\" \n");
            requete = clavier.readLine().trim();

            if (requete.equalsIgnoreCase("quitter")) {
                quit = true;
            } else {
                outFlux.println(requete);
                response = (Response) inFlux.readObject();
                System.out.println("Tentatives restantes : " + response.getTriesLeft());
                System.out.println("Mot à trouver : " + response.getHiddenWord());
                if (response.getStateGame() == 0) {
                    System.out.println("La lettre a déjà été jouée");
                } else if (response.getStateGame() == 1) {
                    System.out.println("La lettre est dans le mot");
                } else if (response.getStateGame() == 2) {
                    System.out.println("Vous avez perdu");
                    quit = true;
                } else if (response.getStateGame() == 3) {
                    System.out.println("Vous avez gagné");
                    quit = true;
                } else if (response.getStateGame() == 4) {
                    System.out.println("La lettre n'est pas dans le mot");
                }
            }
        } while (!quit);

        socket.close();

    }


}
