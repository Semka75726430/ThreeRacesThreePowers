package com.semka.threeraces.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class ConfigManager implements Manager{
    public static FileConfiguration config;
    public static String raceTitan;
    public static String raceKiller;
    public static String raceRunner;
    public static String adminSet;
    public static String adminUnset;
    public static String playerSet;
    public static String playerAlreadySet;
    public static String errorUnknown;
    public static String errorSyntax;
    public static boolean hooksVault = true;
    public static boolean hooksDeluxeMenus = true;
    @Override public boolean start(JavaPlugin pl){
        pl.saveDefaultConfig();
        config = pl.getConfig();
        reloadConfig();
        return true;
    }
    public static void reloadConfig() {
        raceTitan = config.getString("messages.races.titan");
        raceKiller = config.getString("messages.races.killer");
        raceRunner = config.getString("messages.races.runner");
        adminSet = config.getString("messages.admin.race-set");
        adminUnset = config.getString("messages.admin.race-unset");
        playerSet = config.getString("messages.player.race-set");
        playerAlreadySet = config.getString("messages.player.error-race-already-set");
        errorUnknown = config.getString("messages.unknown-race");
        errorSyntax = config.getString("messages.syntax-error");
        hooksVault = config.getBoolean("hooks.vault.enabled");
        hooksDeluxeMenus = config.getBoolean("hooks.deluxemenus.enabled");
    }
    @Override public void stop(JavaPlugin pl) {
        config = null;
    }
}
