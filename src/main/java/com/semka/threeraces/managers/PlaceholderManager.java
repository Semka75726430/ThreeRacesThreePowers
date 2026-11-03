package com.semka.threeraces.managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.plugin.java.JavaPlugin;

import com.semka.threeraces.util.RaceEnum;

public class PlaceholderManager implements Manager {
    @Override public boolean start(JavaPlugin pl){
        return true;
    }
    @Override public void stop(JavaPlugin pl){}
    public static String replace(String str, String playerName, RaceEnum race) {
        return replace(str, playerName, race.getNameFromConfig(), race.name());
    }
    public static String replace(String str, String playerName, String race, String racename) {
        Pattern pattern = Pattern.compile("^\\{[a-zA-Z0-9\\-]+\\}$", Pattern.MULTILINE);
        Matcher matcher =  pattern.matcher(str);
        return matcher.replaceAll((result)->{
            switch(result.group()){
                case "player":
                    return playerName;
                case "race":
                    return race;
                case "racename":
                    return racename;
            }
            return "";
        });
    }
}
