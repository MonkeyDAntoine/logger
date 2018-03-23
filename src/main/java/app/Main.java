package app;

import logger.LogStep;
import logger.LogTry;
import logger.LogVar;
import logger.MyLogger;

import java.util.function.Supplier;

import static logger.MyLogger.var;

public class Main {

    public static final MyLogger LOG = MyLogger.fromClass(Main.class);

    public static void main(String[] args) {

        SomeAppObj precomputedVar = new SomeAppObj();

        LogTry logTry = LOG.trying("some stuff", var("precomputed").is(precomputedVar));

        try {
            SomeAppObj otherVar = new SomeAppObj();
            LogStep loggedStep = LOG.start("A step in a try", var("precomputed").is(precomputedVar), var("otherVar").is(otherVar)).logTimeSpent();
            //doing stuff;
            String importantVar = "so important";
            Thread.sleep(200);
            LOG.end(loggedStep);
            //logTry.success("youpi") ?
            LOG.success(logTry, "youpi", var("importantVar").is(importantVar));
            throw new NullPointerException();
        } catch (Exception e) {
            LOG.failed(logTry,"akéka'", e);
        }
    }
}
