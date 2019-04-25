package cs.cooble.nice.util;

import java.util.Random;

/**
 * Created by Matej on 6.4.2018.
 */
public class ChanceCreator {
    private static Random r = new Random();

    public static int genNumber(String s) {
        s = s.trim();
        if (s.contains("%(") && s.contains(")")) {
            String first = s.substring(0, s.indexOf("%("));
            int probability = Integer.parseInt(first);
            String second = s.substring(s.indexOf("%(") + 2,s.indexOf(")",s.indexOf("%(") + 1));
            if(r.nextInt(100)<probability){
                try {
                    return Integer.parseInt(second);
                }catch (Exception ignored){
                    return procesMinus(second);
                }
            }
            return 0;

        }
        if (s.contains("-")) {
          return procesMinus(s);
        } else return Integer.parseInt(s);
    }
    private static int procesMinus(String s){
        String first = s.substring(0, s.indexOf('-'));
        String second = s.substring(s.indexOf('-') + 1);
        int fi = Integer.parseInt(first);
        int se = Integer.parseInt(second);
        return r.nextInt(se - fi + 1) + fi;
    }
}
