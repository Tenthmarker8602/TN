package me.tenth8602.tN;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AuctionGUI {

    public void openAH(Player player, int page) {
        int size = 54;
        Inventory gui = Bukkit.createInventory(null, size, "AH Page " + page);

        List<AuctionItem> listings = AuctionManager.getListings();

        int itemsPerPage = 45;
        int start = page * itemsPerPage;
        int end = Math.min(start + itemsPerPage, listings.size());

        for (int i = start; i < end; i++) {
            AuctionItem ai = listings.get(i);

            ItemStack item = ItemSerializer.fromBase64(ai.getItemBase64());
            ItemMeta meta = item.getItemMeta();

            meta.setLore(List.of(
                    "§bDiamonds: " + ai.getDiamonds(),
                    "§aEmeralds: " + ai.getEmeralds(),
                    "§7ID: " + ai.getId(),
                    "§eClick to buy"
            ));

            item.setItemMeta(meta);
            gui.addItem(item);
        }

        gui.setItem(45, createButton(Material.ARROW, "§ePrevious"));
        gui.setItem(53, createButton(Material.ARROW, "§eNext"));

        player.openInventory(gui);
    }

    public ItemStack createButton(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}