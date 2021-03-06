package io.github.graves501.chestcleanerx.util.constant;

public enum TimerCommandConstant {
    SET_ACTIVE("setActive"),
    SET_TIME("setTime"),
    TRUE("true"),
    FALSE("false");

    private String stringValue;

    TimerCommandConstant(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getString() {
        return stringValue;
    }
}
