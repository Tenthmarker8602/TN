package me.tenth8602.tN;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.Map;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Audience audience = (Audience) player;
        MiniMessage mm = MiniMessage.miniMessage();
        audience.sendMessage(mm.deserialize("<green><b>Welcome "+player.getDisplayName()+" to Tenth Network"));
        //audience.sendMessage(mm.deserialize("<newline><red><b>This server is under development. transferring"));
        //player.transfer("node-1.ryzenclouds.online",25572);
        if (player.getScoreboard().getObjective("Diamonds")==null) {
            player.getScoreboard().registerNewObjective("Diamonds",  "Diamonds");
            player.getScoreboard().getObjective("Diamonds").setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
        if (player.getScoreboard().getObjective("Emeralds")==null) {
            player.getScoreboard().registerNewObjective("Emeralds",  "Emeralds");
            player.getScoreboard().getObjective("Emeralds").setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
    }
}
