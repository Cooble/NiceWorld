package cs.cooble.nice.chat.command;

import cs.cooble.nice.chat.SlovoManager;
import cs.cooble.nice.entity.Matej;

/**
 * Created by Matej on 5.6.2015.
 */
public class CommandStats extends Command {
    public CommandStats() {
        super("add");
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

            int pocet = Integer.parseInt(s.substring(first));

            if (SlovoManager.getSlovoFrom(s, 0).equals("life"))
                world.getMatej().getStats().addHealth(pocet);
            else if (SlovoManager.getSlovoFrom(s, 0).equals("hunger"))
                world.getMatej().getStats().addHunger(pocet);
            else if (SlovoManager.getSlovoFrom(s, 0).equals("water"))
                world.getMatej().getStats().addWater(pocet);
    }
}
