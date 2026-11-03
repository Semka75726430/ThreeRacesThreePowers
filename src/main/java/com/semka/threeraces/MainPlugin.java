package com.semka.threeraces;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.semka.threeraces.cmds.*;
import com.semka.threeraces.managers.*;

/**
 * MainPlugin - Главный класс плагина
 * 
 */
public final class MainPlugin extends JavaPlugin {
    private MainCommand cmd;
    private AdminCommand adminCmd;
    private static boolean deluxeMenusExists = false;
    private static MainPlugin instance;
    private static Manager[] mgrs;
    public MainPlugin() {
        instance = this;
    }
    @Override public void onEnable() {
        deluxeMenusExists = setupDeluxeMenus();
        
        cmd = new MainCommand();
        getCommand("race").setExecutor(cmd);
        getCommand("race").setTabCompleter(cmd);
        getCommand("раса").setExecutor(cmd);
        getCommand("раса").setTabCompleter(cmd);

        adminCmd = new AdminCommand();
        getCommand("threeraces").setExecutor(adminCmd);
        getCommand("threeraces").setTabCompleter(adminCmd);

        mgrs = new Manager[]{new ConfigManager(), new AdminManager(),new StorageManager(), new EventManager(), new PermissionManager(), new PlaceholderManager()};
        for(Manager mgr : mgrs){
            boolean isStarted = false;
            try{
                isStarted = mgr.start(this);
            }catch (Throwable t){
                getLogger().severe("(in "+mgr.getClass().getCanonicalName()+"#start) "+t.toString());
            }
            if(isStarted){
                getLogger().warning("Менеджер "+mgr.getClass().getSimpleName()+" не запустился");
            }
        }

        getLogger().info("Плагин запущен!");
    }
    @Override public void onDisable() {
        for(Manager mgr : mgrs) {
            try{
                mgr.stop(this);
            }catch (Throwable t){
                getLogger().severe("(in "+mgr.getClass().getCanonicalName()+"#stop) "+t.toString());
            }
        }
        instance = null;
    }
    public boolean setupDeluxeMenus() {
        if (!Bukkit.getPluginManager().isPluginEnabled("DeluxeMenus")) {
            getLogger().warning("Плагин DeluxeMenus не найден, GUI не будет работать");
            return false;
        }
        return true;
    }
    public static void openGUI(Player player) {
        if(!deluxeMenusExists){
            player.sendMessage("§cGUI не существует");
            return;
        }
        String command = "dm open race "+player.getName();
        AdminManager.invokeCmd(command);
    }
    public static void reloadPlugin() {
        instance.reloadConfig();
    }
}