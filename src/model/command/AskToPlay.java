package model.command;

import model.Response;
import server.ServerClientThread;

/**
 * This command is used to ask to play with a player
 */
public class AskToPlay extends BaseCommand {
    private static final String COMMAND = "pw";  // The command name (pw = play with)

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
    public Response run() {
        try {
            ServerClientThread player = this.client.getPlayerByNameInQueue(this.args[0]);  // Get the player by his name (in the queue)
            if (player != null) {  // If the player is not null (if he exists) then ask to play with him
                player.sendMessage("Vous avez été invité à jouer par " + this.client.getPlayerName());
                this.client.setPlayerAsked(player);
                player.setPlayerAsked(this.client);
                return new Response("Demande envoyée à " + player.getPlayerName(), this.client.getEtat());
            } else {  // If the player is null (if he doesn't exist) then return an error
                return new Response("Le joueur n'existe pas", this.client.getEtat());
            }
        } catch (Exception e) {
            return new Response("Une erreur est survenue", this.client.getEtat());
        }
    }

    @Override
    public String isValid() {
        if (this.args.length != 1) {  // If the number of arguments is not 1 (the player name) then return an error
            return "Invalid number of arguments";
        }

        ServerClientThread player = this.client.getPlayerByNameInQueue(this.args[0]);
        if (player == null) {  // If the player is null (if he doesn't exist) then return an error
            return "Player not found";
        } else {  // If the player is not null (if he exists) then return an error too
            if (!this.getAvailablePlayers().contains(player)) {
                return "Player not available";
            }
        }

        return null;
    }
}
