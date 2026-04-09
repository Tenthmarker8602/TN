package me.tenth8602.tN;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("<green><b>Welcome to Tenth Network");
    }
    @EventHandler
    public void onLeave(PlayerKickEvent event) {
        Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " got kicked");
    }
}
