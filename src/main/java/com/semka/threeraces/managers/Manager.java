package com.semka.threeraces.managers;

import org.bukkit.plugin.java.JavaPlugin;

public interface Manager {
    boolean start(JavaPlugin plugin);
    void stop(JavaPlugin plugin);
}
