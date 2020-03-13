package io.github.graves501.chestcleanerx.utils;

import io.github.graves501.chestcleanerx.config.PluginConfiguration;
import io.github.graves501.chestcleanerx.sorting.SortingPattern;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryConverter {

    /**
     * <b>Converts an inventory into a ArrayList of ItemStacks and returns
     * it.</b> Air will get removed.
     *
     * @param inventory The inventory you want to convert into an ArrayList.
     * @return Returns an ArrayList of ItemStacks you got from the inventory.
     * @throws IllegalArgumentException throws if the argument Inventory {@code inv} is equal to
     * null.
     */
    public static ArrayList<ItemStack> getArrayListFormInventory(Inventory inventory) {

        if (inventory == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<ItemStack> list = new ArrayList<>();

        for (ItemStack item : inventory) {
            if (item != null) {
                if (!item.getType().equals(Material.AIR)) {
                    list.add(item);
                }
            }
        }
        return list;
    }

    /**
     * <b>Sets the items of the {@code inventory} to the ItemStacks of the
     * ArrayList {@code items}.</b> The method clears the inventory before putting the items into
     * the inventory.
     *
     * @param inventory The inventory you want to put the items in.
     * @param items The list of items you want to put into the cleared inventory.
     * @param isPlayer true if you are sorting a player inventory.
     * @throws IllegalArgumentException throws if the argument ItemStack {@code items} or the
     * Inventory {@code inv} is equal to null.
     */
    public static void setItemsOfInventory(Inventory inventory, List<ItemStack> items,
        boolean isPlayer,
        SortingPattern pattern) {

        if (items == null || inventory == null) {
            throw new IllegalArgumentException();
        }

        if (!isPlayer) {
            inventory.clear();
        } else {
            for (int i = 9; i < 36; i++) {
                inventory.clear(i);
            }
        }

        int shift = 0;
        int height = inventory.getSize() / 9;
        boolean done = false;
        if (isPlayer) {
            shift = 9;
            height--;
        }

        if (pattern == null) {
            pattern = PluginConfiguration.getInstance().getDefaultSortingPattern();
        }

        switch (pattern) {

            case LEFT_TO_RIGHT_TOP_TO_BOTTOM:

                for (int i = 0; i < items.size(); i++) {
                    inventory.setItem(i + shift, items.get(i));
                }
                break;

            case TOP_TO_BOTTOM_LEFT_TO_RIGHT:

                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < height; y++) {

                        if ((x * height + y) >= items.size()) {
                            done = true;
                            break;
                        }
                        inventory.setItem(y * 9 + x + shift, items.get(x * height + y));

                    }
                    if (done) {
                        break;
                    }
                }
                break;

            case RIGHT_TO_LEFT_BOTTOM_TO_TOP:

                int begin = isPlayer ? 35 : inventory.getSize() - 1;
                int i = 0;

                while (i < items.size()) {

                    inventory.setItem(begin - i, items.get(i));
                    i++;

                }

                break;

            case BOTTOM_TO_TOP_LEFT_TO_RIGHT:

                int itemCounter = 0;

                for (int x = 0; x < 9; x++) {
                    for (int y = height - 1; y >= 0; y--) {

                        if (itemCounter >= items.size()) {
                            done = true;
                            break;
                        }
                        inventory.setItem(y * 9 + x + shift, items.get(itemCounter));
                        itemCounter++;
                    }
                    if (done) {
                        break;
                    }
                }

                break;
        }

    }

    /**
     * Sets the Item Stacks of the ArrayList into the slots of the player-inventory, just effects
     * index 9 (including) to index 35 (including).That means the hotbar or other important slots
     * (armor slots, second hand slot) are getting avoided.
     *
     * @param inventoryItems The list of ItemStacks you want to put into the player inventory. Its
     * size should be <= 27.
     * @param player the player whose inventory will get effected.
     * @throws IllegalArgumentException throws if the argument ItemStack {@code items} or the
     * Inventory {@code inv} is equal to null.
     */
    public static void setPlayerInventory(List<ItemStack> inventoryItems, Player player,
        SortingPattern pattern) {

        if (inventoryItems == null || player == null) {
            throw new IllegalArgumentException();
        }

        setItemsOfInventory(player.getInventory(), inventoryItems, true, pattern);

    }

}
