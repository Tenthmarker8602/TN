package me.tenth8602.tN;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
public class ahExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        // Check player FIRST
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players may use this command");
            return true;
        }

        // OPEN GUI: /ah
        if (args.length == 0) {
            TN plugin = TN.getPlugin(TN.class);
            plugin.getAuctionGUI().openAH(player, 0);
            return true;
        }

        // CREATE LISTING: /ah <diamonds> <emeralds>
        if (args.length >= 2) {
            try {
                int diamonds = Integer.parseInt(args[0]);
                int emeralds = Integer.parseInt(args[1]);

                ItemStack item = player.getInventory().getItemInMainHand();

                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage("§cHold an item!");
                    return true;
                }

                AuctionItem auctionItem = new AuctionItem(
                        player.getUniqueId(),
                        ItemSerializer.toBase64(item),
                        diamonds,
                        emeralds
                );

                AuctionManager.add(auctionItem);

                player.sendMessage("§aItem listed!");
                player.getInventory().setItemInMainHand(null);
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid number!");
            }

            return true;
        }

        // INVALID USAGE
        player.sendMessage("§cUsage:");
        player.sendMessage("§e/ah §7(Open GUI)");
        player.sendMessage("§e/ah <diamonds> <emeralds> §7(List item)");

        return true;
    }
}
