package com.semka.threeraces.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import com.semka.threeraces.MainPlugin;
import com.semka.threeraces.managers.StorageManager;
import com.semka.threeraces.util.RaceEnum;

public class AdminCommand implements TabExecutor {
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0){
            sender.sendMessage("§6ThreeRacesThreePowers §eby Semka2012");
            sender.sendMessage("/threeraces reload\n/threeraces player <player> info\n/threeraces player <player> setrace <race>");
        }else{
            if("reload".equals(args[0])){
                sender.sendMessage("§4Перезагрузка...");
                MainPlugin.reloadPlugin();
                sender.sendMessage("§aПерезагружено!");
            }else if("player".equals(args[0]) && args.length > 2) {
                OfflinePlayer offp = Bukkit.getOfflinePlayer(args[1]);
                if("info".equals(args[2])){
                    RaceEnum race = StorageManager.getRace(offp.getUniqueId());
                    sender.sendMessage("Игрок "+offp.getName());
                    sender.sendMessage("Раса "+race.name());
                }else if("setrace".equals(args[2])){
                    RaceEnum race = RaceEnum.NONE;
                    if(args.length>3){
                        try {
                            race = RaceEnum.valueOf(args[3]);
                        }catch(IllegalArgumentException e){
                            sender.sendMessage("Неизвестная раса");
                        }
                    }
                    StorageManager.setRace(offp.getUniqueId(), race);
                    sender.sendMessage("§aРаса у игрока §b"+offp.getName()+" §aбыла "+ (race == RaceEnum.NONE ? "§cсброшена" : "§4установнена на "+ race.name()));
                }
            }else {
                sender.sendMessage("Syntax error");
            }
        }
        return true;
    }
    @Override public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = null;
        switch (args.length) {
            case 0:
            case 1:
                list = Arrays.asList("reload","player");
                break;
            case 2:
                list = Bukkit.getOnlinePlayers().stream().map((Player p)->p.getName()).collect(Collectors.toList());
                list.addAll(StorageManager.getSavedPlayerNames());
                break;
            case 3:
                list = Arrays.asList("setrace","info");
                break;
            case 4:
                list = Arrays.asList("titan","killer","runner","none");
                break;
            default:
                break;
        }
        return list == null? new ArrayList<String>() : list;
    }
}
