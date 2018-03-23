package logger;

import java.util.function.Supplier;

public class LogVar {

    private final String name;
    private final Object value;

    private LogVar(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    Object getValue() {
        return value;
    }

    public static class Builder {

        private final String name;

        private Builder(String name) {
            this.name = name;
        }

        protected static final LogVar.Builder var(String name) {
            return new Builder(name);
        }

        public Supplier<LogVar> is(Object value) {
            return () -> new LogVar(name, value);
        }
    }
}
