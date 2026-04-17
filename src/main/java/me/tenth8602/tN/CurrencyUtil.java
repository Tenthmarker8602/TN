package me.tenth8602.tN;

import org.bukkit.Material;
import org.bukkit.entity.Player;
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
}