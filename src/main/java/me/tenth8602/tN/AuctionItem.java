package me.tenth8602.tN;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.UUID;

public class AuctionItem {

    private String id; // unique ID
    private UUID seller;
    private String itemBase64;
    private int diamonds;
    private int emeralds;

    public AuctionItem(UUID seller, String itemBase64, int diamonds, int emeralds) {
        this.id = UUID.randomUUID().toString();
        this.seller = seller;
        this.itemBase64 = itemBase64;
        this.diamonds = diamonds;
        this.emeralds = emeralds;
    }

    public String getId() { return id; }
    public UUID getSeller() { return seller; }
    public String getItemBase64() { return itemBase64; }
    public int getDiamonds() { return diamonds; }
    public int getEmeralds() { return emeralds; }
}
