package com.minehorizon.prestigeperks.listener.perks.potion;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PotionQuit implements Listener {
    private final PrestigePerks prestigePerks;
    public PotionQuit(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for(Perks perks : Perks.values()) {
            if(event.getPlayer().hasPermission(perks.getPermission())) {
                if(perks.hasGiveableEffect()) {
                    if(event.getPlayer().hasPotionEffect(perks.getPotionEffectType())) {
                        event.getPlayer().removePotionEffect(perks.getPotionEffectType());
                    }
                }
            }
        }
    }
}
