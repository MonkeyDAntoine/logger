package app;

import java.util.UUID;

public class OtherAppObj {

    private String randomValue;
    private SomeAppObj testLoop;

    public OtherAppObj() {
        this.randomValue = UUID.randomUUID().toString();
    }

    public void setTestLoop(SomeAppObj testLoop) {
        this.testLoop = testLoop;
    }

    @Override
    public String toString() {
        return "OtherAppObj{" +
                "randomValue='" + randomValue + '\'' +
                '}';
    }
}
