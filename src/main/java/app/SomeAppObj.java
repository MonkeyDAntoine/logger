package app;

import java.util.UUID;

public class SomeAppObj {

    private String randomValue;
    private OtherAppObj testLoop;

    public SomeAppObj() {
        this.randomValue = UUID.randomUUID().toString();
        this.testLoop = new OtherAppObj();
        testLoop.setTestLoop(this);
    }

    @Override
    public String toString() {
        return "SomeAppObj{" +
                "randomValue='" + randomValue + '\'' +
                '}';
    }
}
