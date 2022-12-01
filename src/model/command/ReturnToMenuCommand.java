package model.command;

import model.Response2;
import model.states.Menu;

public class ReturnToMenuCommand extends BaseCommand {

    private static final String MENU = "/menu";
    public ReturnToMenuCommand(Command next) {
        super(next);
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
    public Response2 run() {
        this.client.changeState(new Menu());
        return new Response2("You have returned to the menu", this.client.getEtat());
    }

    @Override
    public String isValid() {
        return args.length == 0 ? null : "Invalid number of arguments";
    }
}
