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
        Socket socket = new Socket("localhost", 1234);
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
                switch (response.getStateGame()) {
                    case 0:
                        System.out.println("mauvaise lettre, essais restants" + response.getTriesLeft() + " : \n" + response.getHiddenWord());
                        break;

                    case 1:
                        System.out.println("bonne lettre , essais restants" + response.getTriesLeft() + " \n" + response.getHiddenWord());
                        break;

                    case 2:
                        System.out.println("La lettre est dans le mot et tu as gagné");
                        quit = true;
                        break;
                    case 4:
                        System.out.println("La lettre n'est pas dans le mot et tu as perdu. Le mot était : " + response.getHiddenWord());
                        quit = true;
                        break;
                }


            }
        } while (!quit);

    }


}
