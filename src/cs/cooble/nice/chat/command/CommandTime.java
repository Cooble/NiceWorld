package cs.cooble.nice.chat.command;

import cs.cooble.nice.chat.SlovoManager;
import cs.cooble.nice.core.NiceWorld;

/**
 * Created by Matej on 17.6.2015.
 */
public class CommandTime extends Command {
    public CommandTime() {
        super("time");
    }
    public void action(String s) {
        int first = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c) || c == '-') {
                first = i;
                break;
            }

        }

        double pocet = Double.parseDouble(s.substring(first));

        if (SlovoManager.getSlovoFrom(s, 0).equals("set"))
            NiceWorld.getNiceWorld().getWorld().getTimeManager().setHodin(pocet);
        else if (SlovoManager.getSlovoFrom(s, 0).equals("add"))
            NiceWorld.getNiceWorld().getWorld().getTimeManager().addHodin(pocet);

    }
}
