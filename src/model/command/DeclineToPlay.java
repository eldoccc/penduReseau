package model.command;

import model.Response;

/**
 * This class is the command to decline to play with a player
 */
public class DeclineToPlay extends BaseCommand {
    private static final String COMMAND = "decline";  // The command name

    public DeclineToPlay(String command, String[] args) {
        super(command, args);
    }

    public DeclineToPlay(Command next) {
        super(next);
        this.command_name = COMMAND;
    }

    @Override
    public String getUsage() {
        return COMMAND + " <message> (optional)";
    }

    @Override
    public String getDescription() {
        return "Decline the invitation to play if a player has invited you";
    }

    @Override
    public String getExample() {
        return COMMAND + " I'm busy";
    }

    @Override
    public Response run() {
        // Send the message to the playerAsked if there is one and remove the playerAsked else send an error message (rejected by isValid)
        if (this.client.getPlayerAsked() != null) {
            if (this.args.length > 0) {
                this.client.getPlayerAsked().sendMessage(this.client.getPlayerName() + " a refusé votre invitation : " + this.getArgsAsString());
            } else {
                this.client.getPlayerAsked().sendMessage(this.client.getPlayerName() + " a refusé votre invitation");
            }

            String tmpName = this.client.getPlayerAsked().getPlayerName();
            this.client.getPlayerAsked().setPlayerAsked(null);
            this.client.setPlayerAsked(null);
            return new Response("Vous avez refusé l'invitation de " + tmpName, this.client.getEtat());
        } else {
            return new Response("Vous n'avez pas été invité à jouer", this.client.getEtat());
        }
    }

    @Override
    public String isValid() {
        // If the player has been invited to play
        if (this.client.getPlayerAsked() == null) {
            return "You have not been invited to play";
        }
        return null;
    }
}
