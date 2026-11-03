package com.semka.threeraces.cmds;

import java.util.*;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import com.semka.threeraces.managers.ConfigManager;
import com.semka.threeraces.managers.PermissionManager;
import com.semka.threeraces.managers.PlaceholderManager;
import com.semka.threeraces.managers.StorageManager;
import com.semka.threeraces.util.RaceEnum;

public class MainCommand implements TabExecutor {
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
                
            if(args.length == 0){
                com.semka.threeraces.MainPlugin.openGUI(player);
            } else {
                UUID uuid = player.getUniqueId();
                RaceEnum raceEnum = StorageManager.getRace(uuid);
                if(raceEnum != RaceEnum.NONE && !PermissionManager.hasPermission(player, "threeraces.changeafter")) {
                    player.sendMessage(PlaceholderManager.replace(ConfigManager.playerAlreadySet, player.getName(), raceEnum));
                }
                RaceEnum race = RaceEnum.NONE;
                switch(args[0]){
                    case"титан":case"titan":case"1":
                        race = RaceEnum.TITAN;
                        break;
                    case"раннер":case"runner":case"3":
                        race = RaceEnum.RUNNER;
                        break;
                    case"убийца":case"killer":case"2":
                        race = RaceEnum.KILLER;
                        break;
                    default:
                        player.sendMessage(PlaceholderManager.replace(ConfigManager.errorUnknown, player.getName(), "", args[0]));
                        return true;
                }
                StorageManager.setRace(uuid, race);
                player.sendMessage(PlaceholderManager.replace(ConfigManager.playerSet, player.getName(), raceEnum));
            }
        }else {
            sender.sendMessage("§4Команда только для игроков!");
        }
        return true;
    }
    @Override public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Arrays.asList("titan","killer","runner");
    }
}
