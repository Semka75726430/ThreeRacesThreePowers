package com.semka.threeraces;

import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.semka.threeraces.managers.StorageManager;
import com.semka.threeraces.util.RaceEnum;

public class PlayerListener implements Listener {
    public static NamespacedKey speedKey = new NamespacedKey("threeraces", "speedmod");
    public static AttributeModifier modifier_speed = new AttributeModifier(
            speedKey,
            0.15, // Увеличит базовую скорость на 15%
            AttributeModifier.Operation.ADD_SCALAR
    );
    public static NamespacedKey resistanceKey = new NamespacedKey("threeraces", "resistancemod");
    public static AttributeModifier modifier_resistance = new AttributeModifier(
            resistanceKey,
            0.15, // Увеличит базовую защиту от урона на 15%
            AttributeModifier.Operation.ADD_SCALAR
    );
    public static NamespacedKey damageKey = new NamespacedKey("threeraces", "damagemod");
    public static AttributeModifier modifier_damage = new AttributeModifier(
            damageKey,
            0.15, // Увеличит базовый урон на 15%
            AttributeModifier.Operation.ADD_SCALAR
    );
    @EventHandler
    public void join(PlayerJoinEvent evt){
        restorePowers(evt.getPlayer());
    }
    public void restorePowers(Player player) {
        UUID uuid = player.getUniqueId();
        AttributeInstance attribute;
        RaceEnum race = StorageManager.getRace(uuid);
        attribute = player.getAttribute(Attribute.ATTACK_DAMAGE);
        if (attribute != null) {
            if(race == RaceEnum.KILLER) {
                if (!attribute.getModifiers().contains(modifier_damage))attribute.addModifier(modifier_damage);
            }else{
                attribute.removeModifier(modifier_damage);
            }
        }
        attribute = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (attribute != null) {
            if(race == RaceEnum.RUNNER) {
                if (!attribute.getModifiers().contains(modifier_speed))attribute.addModifier(modifier_speed);
            }else{
                attribute.removeModifier(modifier_speed);
            }
        }
        attribute = player.getAttribute(Attribute.ARMOR);
        if (attribute != null) {
            if(race == RaceEnum.TITAN) {
                if (!attribute.getModifiers().contains(modifier_resistance))attribute.addModifier(modifier_resistance);
            }else{
                attribute.removeModifier(modifier_resistance);
            }
        }
    }

}
