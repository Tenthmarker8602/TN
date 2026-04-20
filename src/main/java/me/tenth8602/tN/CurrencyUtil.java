package me.tenth8602.tN;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CurrencyUtil {

    public static int count(Player p, Material mat) {
        return p.getInventory().all(mat)
                .values()
                .stream()
                .mapToInt(ItemStack::getAmount)
                .sum();
    }

    public static void remove(Player p, Material mat, int amount) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null || item.getType() != mat) continue;

            int take = Math.min(item.getAmount(), amount);
            item.setAmount(item.getAmount() - take);
            amount -= take;

            if (amount <= 0) break;
        }
    }
    public static void removeDiamonds(Player player, int amount) {
        removeCurrency(player, amount, Material.DIAMOND, Material.DIAMOND_BLOCK);
    }

    public static void removeEmeralds(Player player, int amount) {
        removeCurrency(player, amount, Material.EMERALD, Material.EMERALD_BLOCK);
    }
    private static void removeCurrency(Player player, int amount,
                                       Material itemMat,
                                       Material blockMat) {

        Inventory inv = player.getInventory();

        // 1. Remove loose items first
        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType() != itemMat) continue;

            int take = Math.min(amount, item.getAmount());
            item.setAmount(item.getAmount() - take);
            amount -= take;

            if (amount <= 0) return;
        }

        // 2. Use blocks if needed
        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType() != blockMat) continue;

            int blocks = item.getAmount();

            for (int i = 0; i < blocks; i++) {
                if (amount <= 0) return;

                // Remove one block
                item.setAmount(item.getAmount() - 1);

                // Convert to 9 items
                int value = 9;

                if (value > amount) {
                    // Give leftover back
                    int leftover = value - amount;
                    inv.addItem(new ItemStack(itemMat, leftover));
                    amount = 0;
                    return;
                }

                amount -= value;
            }
        }
    }
}