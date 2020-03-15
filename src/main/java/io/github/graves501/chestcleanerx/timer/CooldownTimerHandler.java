package io.github.graves501.chestcleanerx.timer;

import io.github.graves501.chestcleanerx.config.PluginConfiguration;
import io.github.graves501.chestcleanerx.main.PluginMain;
import io.github.graves501.chestcleanerx.utils.enums.PluginPermission;
import io.github.graves501.chestcleanerx.utils.logging.PluginLoggerUtil;
import io.github.graves501.chestcleanerx.utils.messages.InGameMessage;
import io.github.graves501.chestcleanerx.utils.messages.InGameMessageHandler;
import io.github.graves501.chestcleanerx.utils.messages.InGameMessageType;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CooldownTimerHandler {

    private CooldownTimerHandler() {
    }

    private static final Logger logger = JavaPlugin.getPlugin(PluginMain.class).getLogger();

    private static ArrayList<CooldownTimer> cooldownTimerList = new ArrayList<>();

    public static void update() {
        if (PluginConfiguration.getInstance().isCooldownTimerActive()) {
            for (CooldownTimer cooldownTimer : cooldownTimerList) {
                countDownOneSecond(cooldownTimer);
            }

            cooldownTimerList
                .removeIf(cooldownTimer -> cooldownTimer.getCooldownTimeInSeconds() <= 0);
        }
    }

    public static boolean isPlayerOnCooldownTimerList(final Player player) {
        //TODO use streams
        for (CooldownTimer cooldownTimer : cooldownTimerList) {
            if (cooldownTimer.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public static int getCooldownTimeForPlayer(final Player player) {
        //TODO use streams
        for (CooldownTimer cooldownTimer : cooldownTimerList) {
            if (cooldownTimer.getPlayer().equals(player)) {
                return cooldownTimer.getCooldownTimeInSeconds();
            }
        }
        return 0;
    }

    private static void addPlayerToCooldownTimer(final Player player) {
        cooldownTimerList.add(new CooldownTimer(player,
            PluginConfiguration.getInstance().getCooldownTimeInSeconds()));
    }

    public static boolean isPlayerAllowedToUseSort(final Player player) {

        if (isCoolDownTimerActive() && !playerHasTimerNoEffectPermission(player)) {
            if (isPlayerOnCooldownTimerList(player)) {
                logErrorAndSendSortingCooldownMessageToPlayer(player);
                return false;
            }
            addPlayerToCooldownTimer(player);
        }

        return true;
    }

    private static void logErrorAndSendSortingCooldownMessageToPlayer(final Player player) {
        PluginLoggerUtil
            .logPlayerInfo(logger, player, "Sorting not allowed due to cooldown.");

        InGameMessageHandler.sendMessageToPlayer(player,
            InGameMessageType.ERROR,
            InGameMessage.SORTING_ON_COOLDOWN,
            String.valueOf(getCooldownTimeForPlayer(player)));
    }

    private static boolean isCoolDownTimerActive() {
        return PluginConfiguration.getInstance().isCooldownTimerActive();
    }

    private static boolean playerHasTimerNoEffectPermission(final Player player) {
        return player.hasPermission(PluginPermission.TIMER_NO_EFFECT.getString());
    }

    private static void countDownOneSecond(final CooldownTimer cooldownTimer) {
        cooldownTimer.setCooldownTimeInSeconds(cooldownTimer.getCooldownTimeInSeconds() - 1);
    }

}