package logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class MyLogger {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        //MAPPER.registerModule(new ParameterNamesModule());

        // make private fields of Person visible to Jackson
        MAPPER.setVisibility(FIELD, ANY);
    }


    private static final MyLogger LOG = new MyLogger(MyLogger.class);

    private final Logger root;

    public static final MyLogger fromClass(Class<?> clazz) {
        return new MyLogger(clazz);
    }

    private MyLogger(Class<?> clazz) {
        this.root = Logger.fromClass(clazz);
    }

    public static final LogVar.Builder var(String name) {
        return LogVar.Builder.var(name);
    }

    public LogTry trying(String what, Supplier<LogVar>... vars) {
        root.info("Trying " + what);
        logVars(vars);
        return new LogTry(what);
    }

    public LogStep start(String step, Supplier<LogVar>... vars) {
        LogStep logStep = new LogStep(step);
        root.info("Starting : " + step);
        logVars(vars);
        return logStep;
    }

    public void end(LogStep logStep, Supplier<LogVar>... vars) {
        logStep.end();
        logVars(vars);
        String step = logStep.getStep();
        root.info("End : " + step);
        if (logStep.isLogTime()) {
            root.debug("Step " + step + " took " + logStep.getTimeSpent() + " ms");
        }
    }

    public void logVars(Supplier<LogVar>... vars) {
        if (root.isDebugEnabled()) {
            for (int i = 0; i < vars.length; i++) {
                Supplier<LogVar> lazyVar = vars[i];
                if (lazyVar != null) {
                    LogVar var = lazyVar.get();
                    Object value = var.getValue();
                    root.debug(var.getName() + "  [as String] : " + Objects.toString(value + ""));
                    if (value != null) {
                        try {
                            root.debug(var.getName() + " [as JSON] : " + MAPPER.writeValueAsString(value));
                        } catch (JsonProcessingException e) {
                            if (root != LOG.root) {
                                LOG.warn("Cannot log the var " + var.getName() + " as JSON : " + e.getMessage(), lazyVar);
                            }
                            //e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void warn(String message, Supplier<LogVar>... vars) {
        logVars(vars);
        root.warn(message);
    }

    public void success(LogTry logTry, String message, Supplier<LogVar>... vars) {
        logVars(vars);
        root.info("Try [" + logTry.getWhat() + "] finished : " + message);
    }

    public void failed(LogTry logTry, String why, Exception exceptionRaised) {
        root.error("Error while trying [" + logTry.getWhat() + "] because [" + why + "]", exceptionRaised);
    }
}
