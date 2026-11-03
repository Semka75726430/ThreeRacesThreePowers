package com.semka.threeraces.managers;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.semka.threeraces.PlayerListener;

public class EventManager implements Manager {
    private PlayerListener listener;
    @Override public boolean start(JavaPlugin pl) {
        if(listener != null) return false;
        listener = new PlayerListener();
        pl.getServer().getPluginManager().registerEvents(listener, pl);
        return true;
    }
    @Override public void stop(JavaPlugin pl) {
        HandlerList.unregisterAll(pl);
    }
}
