package me.tenth8602.tN;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class AuctionManager {

    private static final List<AuctionItem> listings = new ArrayList<>();
    private static final Gson gson = new Gson();
    private static File file;

    public static void init(File dataFolder) {
        file = new File(dataFolder, "ah.json");
        load();
    }

    public static List<AuctionItem> getListings() {
        return listings;
    }

    public static void add(AuctionItem item) {
        listings.add(item);
        save();
    }

    public static void remove(AuctionItem item) {
        listings.remove(item);
        save();
    }

    public static void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(listings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!file.exists()) return;

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<List<AuctionItem>>(){}.getType();
            List<AuctionItem> loaded = gson.fromJson(reader, type);
            if (loaded != null) listings.addAll(loaded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
