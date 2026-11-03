package com.semka.threeraces.managers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.semka.threeraces.util.RaceEnum;
import com.semka.threeraces.util.RaceStorage;

public class StorageManager implements Manager {
    private BukkitTask saveTask;
    private static RaceStorage storage;
    private static final Map<String,UUID> playerCache = new ConcurrentHashMap<String,UUID>();
    private static JavaPlugin plugin;
    @Override public boolean start(JavaPlugin pl) {
        plugin = pl;
        saveTask = new BukkitRunnable() {
            @Override
            public void run() {
                storage.save();
            }
        }.runTaskTimer(pl, 0L, 60*60*20L);
        storage = new RaceStorage(pl.getDataFolder());
        boolean result = storage.load();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(pl, () -> {
            for(UUID uuid : storage._keySet()) {
                playerCache.put(Bukkit.getOfflinePlayer(uuid).getName(),uuid);
            }
        });
        return result;
    }
    @Override public void stop(JavaPlugin pl) {
        storage.save();
        if (saveTask != null)saveTask.cancel();
        storage = null;
    }
    public static RaceEnum getRace(UUID uuid) {
        return storage.getRace(uuid);
    }
    public static void setRace(UUID uuid, RaceEnum raceEnum){
        storage.setRace(uuid, raceEnum);
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            playerCache.put(Bukkit.getOfflinePlayer(uuid).getName(),uuid);
        });
    }
    public static List<String> getSavedPlayerNames() {
        return new ArrayList<>(playerCache.keySet());
    }
}
