package com.semka.threeraces.managers;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class PermissionManager implements Manager {
    private static Permission perms;
    @Override public boolean start(JavaPlugin pl){
        if(!ConfigManager.hooksVault)return true;
        if (pl.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> rsp = pl.getServer().getServicesManager().getRegistration(Permission.class);
            perms = rsp.getProvider();
        }
        if (perms == null) {
            pl.getLogger().warning("Vault не подключён");
            return false;
        }
        
        return true;
    }
    @Override public void stop(JavaPlugin pl) {
        perms = null;
    }
    public static boolean hasPermission(CommandSender sender, String perm) {
        if(perms != null)return perms.has(sender, perm);
        return sender.hasPermission(perm);
    }
}
