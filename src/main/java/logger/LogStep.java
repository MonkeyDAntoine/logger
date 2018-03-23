package logger;

import java.util.function.Supplier;

public class LogStep {

    private final String step;
    private final Long startedAt;
    private boolean logTime;
    private Long timeSpent;

    public LogStep(String step) {
        this.step = step;
        this.logTime = false;
        this.startedAt = System.currentTimeMillis();
    }

    String getStep() {
        return step;
    }

    Long getTimeSpent() {
        return timeSpent;
    }

    public LogStep logTimeSpent() {
        this.logTime = true;
        return this;
    }

    void end() {
        if( isLogTime()) {
            this.timeSpent = System.currentTimeMillis() - this.startedAt;
        }
    }

    boolean isLogTime() {
        return logTime;
    }


}
