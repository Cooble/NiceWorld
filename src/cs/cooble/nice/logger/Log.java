package cs.cooble.nice.logger;

/**
 * Created by Matej on 28.12.2016.
 */
public class Log {
    private static boolean onlyClass = true;
    private static boolean enabled = true;
    private static boolean isFromInnerMethod;
    private static boolean printErrorTrace=false;

    public static void setWholePath(boolean isDebugging) {
        onlyClass = !isDebugging;
    }

    public static void setPathPrintEnabled(boolean enabled) {
        Log.enabled = enabled;
    }

    public static void println(String o, LogType logType) {
        if (!enabled) {
            println(o);
        }
        if(logType.equals(LogType.ERROR)&&printErrorTrace){
            printStackTrace(o);
            return;
        }
        String location = "";
        int index = isFromInnerMethod ? 2 : 1;
        isFromInnerMethod = false;
        try {
            if (onlyClass) {
                location = new Exception().getStackTrace()[index].getFileName();
                location = location.substring(0, location.length() - 5);
            } else location = new Exception().getStackTrace()[index].toString();

        } catch (Exception e) {
            e.getStackTrace();
        }

        System.out.println(color(logType.COLOR) + (logType.equals(LogType.DEFAULT) ? "" : logType.NAME + "->") + "[" + location + "]: " + o + color(ChatColor.CLEAR));
    }

    public static void println(String o) {
        isFromInnerMethod = true;
        println(o, LogType.DEFAULT);
    }

    public static void printStackTrace(String title) {
        printStackTrace(title, LogType.DEFAULT);
    }

    public static void printStackTrace(String title, LogType logType) {
        StackTraceElement[] s = new Exception().getStackTrace();
        System.out.println(color(logType.COLOR)+"StackTrace: " + title);
        for (int i = 1; i < s.length; i++) {
            System.out.println(color(logType.COLOR)+"\t"+s[i].toString());
        }
        System.out.println(color(logType.COLOR) +"=======================================");
    }

    public static void println(Object o) {
        println(o.toString());
    }

    private static String color(ChatColor chatColor) {
        if (chatColor.equals(ChatColor.WHITE))
            return "";
        return (char) 27 + "[" + chatColor + "m";
    }

    public enum LogType {
        DEFAULT("DEFAULT", ChatColor.WHITE),
        WARN("WARN", ChatColor.YELLOW),
        ERROR("ERROR", ChatColor.RED);

        private final String NAME;
        private final ChatColor COLOR;

        LogType(String name, ChatColor color) {
            NAME = name;
            COLOR = color;
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    private enum ChatColor {
        BLACK(30),
        RED(31),
        GREEN(32),
        YELLOW(33),
        BLUE(34),
        WHITE(37),
        BOLD(1),
        STOP_BOLD(21),
        UNDERLINE(4),
        STOP_UNDERLINE(24),
        CLEAR(0);

        private int ID;

        ChatColor(int i) {
            ID = i;

        }

        @Override
        public String toString() {
            return ID + "";
        }
    }
}
