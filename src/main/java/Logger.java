import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
    public static void info(String info, PrintStream ps) {
        ps.println("["+getTime()+"] [Server Thread / INFO] "+info);
    }
    public static void debug(String info, PrintStream ps) {
        ps.println("["+getTime()+"] [Server Thread / DEBUG] "+info);
    }
    public static void error(String info, PrintStream ps) {
        ps.println("["+getTime()+"] [Server Thread / ERROR] "+info);
    }
    public static void fatal(String info, PrintStream ps) {
        ps.println("["+getTime()+"] [Server Thread / FATAL] "+info);
    }
}
