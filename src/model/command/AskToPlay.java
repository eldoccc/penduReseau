package model.command;

import model.Response2;
import server.ServerClientThread;

public class AskToPlay extends BaseCommand {
    private static final String COMMAND = "pw";

    public AskToPlay(String command, String[] args) {
        super(command, args);
    }

    public AskToPlay(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <player_name>";
    }

    @Override
    public String getDescription() {
        return "Choose a player to play with (player_name is the name of the player)";
    }

    @Override
    public String getExample() {
        return COMMAND + " Thibault";
    }

    @Override
    public Response2 run() {
        try {
            ServerClientThread player = this.client.getPlayerByNameInQueue(this.args[0]);
            if (player != null) {
                player.sendMessage("Vous avez été invité à jouer par " + this.client.getPlayerName());
                this.client.setPlayerAsked(player);
                player.setPlayerAsked(this.client);
                return new Response2("Demande envoyée à " + player.getPlayerName(), this.client.getEtat());
            } else {
                return new Response2("Le joueur n'existe pas", this.client.getEtat());
            }
        } catch (Exception e) {
            return new Response2("Une erreur est survenue", this.client.getEtat());
        }
    }

    @Override
    public String isValid() {
        if (this.args.length != 1) {
            return "Invalid number of arguments";
        }

        ServerClientThread player = this.client.getPlayerByNameInQueue(this.args[0]);
        if (player == null) {
            return "Player not found";
        } else {
            if (!this.getAvailablePlayers().contains(player)) {
                return "Player not available";
            }
        }

        return null;
    }
}
