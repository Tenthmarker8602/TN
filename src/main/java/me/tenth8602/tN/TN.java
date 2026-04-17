package me.tenth8602.tN;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import java.util.Objects;
import com.google.gson.Gson;
public final class TN extends JavaPlugin {
    private AuctionGUI auctionGUI;
    @Override
    public void onEnable() {
        // Plugin startup
        auctionGUI = new AuctionGUI();
        MiniMessage mm = MiniMessage.miniMessage();
        Audience audience = (Audience) Bukkit.getConsoleSender();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Audience audience2 = (Audience) player;
                    Scoreboard board = player.getScoreboard();
                    Objective obj = board.getObjective("Diamonds");
                    if (obj == null) continue;
                    Objective obj2 = board.getObjective("Emeralds");
                    if (obj2 == null) continue;
                    int amtEmr = obj2.getScore(player.getName()).getScore();
                    int amtDia = obj.getScore(player.getName()).getScore();
                    audience2.sendActionBar(mm.deserialize("<blue><b>DIAMONDS: "+amtDia+" - EMERALDS: "+amtEmr));
                }
            }}.runTaskTimer(TN.getPlugin(TN.class),0L,1L);
        audience.sendMessage(mm.deserialize("<gradient:#FF0000:#0000FF><b>TN plugin ACTIVE"));
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        Objects.requireNonNull(getCommand("test")).setExecutor(new testExecutor());
        Objects.requireNonNull(getCommand("ah")).setExecutor(new ahExecutor());
        AuctionManager.init(getDataFolder());
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int amtDia = player.getInventory().all(Material.DIAMOND)
                            .values()
                            .stream()
                            .mapToInt(ItemStack::getAmount)
                            .sum();
                    amtDia += 9 * player.getInventory().all(Material.DIAMOND_BLOCK)
                            .values()
                            .stream()
                            .mapToInt(ItemStack::getAmount)
                            .sum();
                    audience.sendMessage(mm.deserialize("<blue><b>"+player.getDisplayName()+" has :"+amtDia+" Diamonds"));
                    int amtEmr = player.getInventory().all(Material.EMERALD)
                            .values()
                            .stream()
                            .mapToInt(ItemStack::getAmount)
                            .sum();
                    amtEmr += 9* player.getInventory().all(Material.EMERALD_BLOCK)
                            .values()
                            .stream()
                            .mapToInt(ItemStack::getAmount)
                            .sum();
                    audience.sendMessage(mm.deserialize("<green><b>"+player.getDisplayName()+" has :"+amtEmr+" Emeralds"));

                    Scoreboard board = player.getScoreboard();

                    Objective obj = board.getObjective("Diamonds");
                    if (obj == null) continue;
                    Objective obj2 = board.getObjective("Emeralds");
                    if (obj2 == null) continue;
                    obj2.getScore(player.getName()).setScore(amtEmr);
                    obj.getScore(player.getName()).setScore(amtDia);
                    PlayerData playerData = new PlayerData(player.getUniqueId(), amtDia, amtEmr);
                    playerData.save(getDataFolder());
                }
            }
        }.runTaskTimer(TN.getPlugin(TN.class), 0L, 10L);

    }
    public AuctionGUI getAuctionGUI() {
        return auctionGUI;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MiniMessage mm = MiniMessage.miniMessage();
        Audience audience = (Audience) Bukkit.getConsoleSender();
        audience.sendMessage(mm.deserialize("<gradient:#0000FF:#FF0000><b>TN plugin DEACTIVATED"));
    }
}
