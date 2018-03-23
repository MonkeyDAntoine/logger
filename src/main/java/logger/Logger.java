package logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {

    private boolean warnEnabled;

    boolean isInfoEnabled() {
        return CURRENT_LEVEL.ordinal() <= Level.INFO.ordinal();
    }

    public boolean isDebugEnabled() {
        return CURRENT_LEVEL.ordinal() <= Level.DEBUG.ordinal();
    }

    public boolean isErrorEnabled() {
        return CURRENT_LEVEL.ordinal() <= Level.ERROR.ordinal();
    }

    public boolean isWarnEnabled() {
        return CURRENT_LEVEL.ordinal() <= Level.WARN.ordinal();
    }

    enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    private static final Level CURRENT_LEVEL = Level.DEBUG;

    private final Class<?> clazz;

    public static final Logger fromClass(Class<?> clazz) {
        return new Logger(clazz);
    }

    private Logger(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void info(String message) {
        if (isInfoEnabled()) {
            System.out.println(clazz + " - [INFO] " + message);
        }
    }

    public void debug(String message) {
        if (isDebugEnabled()) {
            System.out.println(clazz + " - [DEBUG] " + message);
        }
    }

    public void warn(String message) {
        if (isWarnEnabled()) {
            System.out.println(clazz + " - [WARN] " + message);
        }
    }

    public void error(String message, Throwable e) {
        if (isErrorEnabled()) {
            System.out.println(clazz + " - [ERROR] " + message);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
        }
    }
}
