package io.github.graves501.chestcleanerx.utils.enums;

public enum Permission {
    BLACKLIST("chestcleaner.command.blacklist"),
    CLEAN_INVENTORY("chestcleaner.command.cleanInventory"),
    COOLDOWN_TIMER("chestcleaner.command.cooldownTimer"),
    TIMER_NO_EFFECT("chestcleaner.timer.noeffect");

    private String stringValue;

    Permission(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getString() {
        return stringValue;
    }
}
