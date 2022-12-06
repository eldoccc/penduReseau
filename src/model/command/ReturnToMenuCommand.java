package model.command;

import model.Response;
import model.states.Menu;

/**
 * This class is the command that return to the menu in the end of a game
 */
public class ReturnToMenuCommand extends BaseCommand {

    private static final String MENU = "menu";  // The command name
    public ReturnToMenuCommand(Command next) {
        super(next);
        this.command_name = MENU;
    }

    @Override
    public String getUsage() {
        return MENU;
    }

    @Override
    public String getDescription() {
        return "Return to the menu";
    }

    @Override
    public String getExample() {
        return MENU;
    }

    @Override
    public Response run() {
        this.client.setEtat(new Menu());  // Set the state of the client to the menu
        this.client.joinMenu();  // Join the menu
        return new Response("You have returned to the menu", this.client.getEtat());
    }

    @Override
    public String isValid() {  // Valid if there is no arguments
        return args.length == 0 ? null : "Invalid number of arguments";
    }
}
