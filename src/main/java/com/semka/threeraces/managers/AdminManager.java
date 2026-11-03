package com.semka.threeraces.managers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminManager implements Manager {
    @Override public boolean start(JavaPlugin pl) {
        return true;
    }
    @Override public void stop(JavaPlugin pl) {
    }
    public static void invokeCmd(String command) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
