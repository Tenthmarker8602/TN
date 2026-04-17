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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (Bukkit.getConsoleSender() == commandSender) {
            commandSender.sendMessage("Only players may use this command");
        }
        else {
            Player player = (Player) commandSender;
            TN plugin = TN.getPlugin(TN.class);

            plugin.getAuctionGUI().openAH(player, 0);
        }
        return true;
    }
}
