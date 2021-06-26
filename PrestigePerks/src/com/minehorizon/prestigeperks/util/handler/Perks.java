package com.minehorizon.prestigeperks.util.handler;

import org.bukkit.potion.PotionEffectType;

public enum Perks {
    AUTOSMELT("autosmelt", "prestigeperks.autosmelt", false),
    NOFOODLOSS("nofoodloss", "prestigeperks.nofoodloss", false),
    DOUBLEDROPS("doubledrops", "prestigeperks.doubledrops", false),
    JUMPBOOST("jumpboost", "prestigeperks.jumpboost", true, PotionEffectType.JUMP, 0),
    AUTOPICKUP("autopickup", "prestigeperks.autopickup", false),
    HEADHUNTER("headhunter", "prestigeperks.headhunter", false);

    String configIdentifier;
    String permission;
    boolean hasGiveableEffect;
    PotionEffectType potionEffectType;
    int power;

    Perks(String configIdentifier, String permission, boolean hasGiveableEffect) {
        this.configIdentifier = configIdentifier;
        this.permission = permission;
        this.hasGiveableEffect = hasGiveableEffect;
    }

    Perks(String configIdentifier, String permission, boolean hasGiveableEffect, PotionEffectType potionEffectType, int power) {
        this.configIdentifier = configIdentifier;
        this.permission = permission;
        this.hasGiveableEffect = hasGiveableEffect;
        this.potionEffectType = potionEffectType;
        this.power = power;
    }

    public String getConfigIdentifier() {
        return configIdentifier;
    }

    public String getPermission() {
        return permission;
    }

    public boolean hasGiveableEffect() {
        return hasGiveableEffect;
    }

    public PotionEffectType getPotionEffectType() {
        return potionEffectType;
    }

    public int getPower() {
        return power;
    }
}
