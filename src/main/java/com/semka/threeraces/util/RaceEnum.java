package com.semka.threeraces.util;

import com.semka.threeraces.managers.ConfigManager;

public enum RaceEnum {
    NONE(0),
    TITAN(1),
    KILLER(2),
    RUNNER(3);

    private final int id;

    RaceEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Быстрое получение enum по его 2-битному ID
    public static RaceEnum fromId(int id) {
        switch (id) {
            case 1: return TITAN;
            case 2: return KILLER;
            case 3: return RUNNER;
            default: return NONE;
        }
    }
    
    public String getNameFromConfig() {
        switch(id){
            case 1: return ConfigManager.raceTitan;
            case 2: return ConfigManager.raceKiller;
            case 3: return ConfigManager.raceRunner;
            default: return "";
        }
    }
}
