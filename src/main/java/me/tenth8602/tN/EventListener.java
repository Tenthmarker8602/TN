package me.tenth8602.tN;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import me.tenth8602.tN.AuctionGUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.tenth8602.tN.CurrencyUtil.*;
public class EventListener implements Listener {
    private final AuctionGUI gui = new AuctionGUI();
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
    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getView().getTitle() == null) return;
        if (!e.getView().getTitle().startsWith("AH Page")) return;

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        // page parsing safely
        String[] split = e.getView().getTitle().split(" ");
        int page;
        try {
            page = Integer.parseInt(split[2]);
        } catch (Exception ex) {
            return;
        }

        // NAVIGATION
        if (clicked.getType() == Material.ARROW) {

            String name = clicked.getItemMeta().getDisplayName();

            if (name.contains("Next")) {
                gui.openAH(player, page + 1);
            } else {
                gui.openAH(player, Math.max(0, page - 1));
            }
            return;
        }

        // ITEM CLICK (BUY LOGIC)
        List<String> lore = clicked.getItemMeta().getLore();
        if (lore == null) return;

        String idLine = lore.stream()
                .filter(line -> line.contains("ID:"))
                .findFirst()
                .orElse(null);

        if (idLine == null) return;

        String id = idLine.replace("§7ID: ", "").trim();

        for (AuctionItem ai : AuctionManager.getListings()) {

            if (ai.getId().equals(id)) {

                int diaCost = ai.getDiamonds();
                int emrCost = ai.getEmeralds();

                int playerDia = CurrencyUtil.count(player, Material.DIAMOND);
                int playerEmr = CurrencyUtil.count(player, Material.EMERALD);

                if (playerDia < diaCost || playerEmr < emrCost) {
                    player.sendMessage("§cNot enough currency!");
                    return;
                }

                CurrencyUtil.remove(player, Material.DIAMOND, diaCost);
                CurrencyUtil.remove(player, Material.EMERALD, emrCost);

                player.getInventory().addItem(
                        ItemSerializer.fromBase64(ai.getItemBase64())
                );

                AuctionManager.remove(ai);

                player.sendMessage("§aPurchased!");
                player.closeInventory();
                return;
            }
        }
    }
}
