package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        String requete = "";
        System.out.println("Salut à toi JOUEUR1, bienvenue sur le jeu du pendu ! \n");
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket("locahost", 1234);
        BufferedReader inFlux = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream outFlux = new PrintStream(socket.getOutputStream());

        boolean quit = false;
        do{
            System.out.println("Tapes la lettre à envoyer au serveur ou quittes la conversation avec \"quitter\" \n");
            requete = clavier.readLine().trim();

            if(requete.equalsIgnoreCase("quitter")){
                quit = true;
            }
            else{
                outFlux.println(requete);
                boolean response = Boolean.parseBoolean(inFlux.readLine());
            }
        }while(!quit);

    }


}
