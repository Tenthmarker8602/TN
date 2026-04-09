package me.tenth8602.tN;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("<green><b>Welcome to Tenth Network");
        player.sendMessage("<newline><red><b>This server is under development. transfering");
        player.transfer("node-1.ryzenclouds.online",25572);
    }
}
