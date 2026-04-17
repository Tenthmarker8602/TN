package me.tenth8602.tN;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.UUID;

public class PlayerData {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final UUID uuid;
    private int diamonds;
    private int emeralds;

    public PlayerData(UUID uuid, int diamonds, int emeralds) {
        this.uuid = uuid;
        this.diamonds = diamonds;
        this.emeralds = emeralds;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public int getEmeralds() {
        return emeralds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public void setEmeralds(int emeralds) {
        this.emeralds = emeralds;
    }

    // 🔽 SAVE to file
    public void save(File dataFolder) {
        File folder = new File(dataFolder, "playerdata");
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, uuid.toString() + ".json");

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔽 LOAD from file
    public static PlayerData load(File dataFolder, UUID uuid) {
        File folder = new File(dataFolder, "playerdata");
        File file = new File(folder, uuid.toString() + ".json");

        if (!file.exists()) {
            // return empty/default data if file doesn't exist
            return new PlayerData(uuid, 0, 0);
        }

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, PlayerData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PlayerData(uuid, 0, 0);
    }
}