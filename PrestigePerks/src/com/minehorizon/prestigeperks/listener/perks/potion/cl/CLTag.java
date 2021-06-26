package com.minehorizon.prestigeperks.listener.perks.potion.cl;

import com.SirBlobman.combatlogx.event.PlayerTagEvent;
import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CLTag implements Listener {
    private final PrestigePerks prestigePerks;
    public CLTag(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onTag(PlayerTagEvent event) {
        for(Perks perks : Perks.values()) {
            if(perks.hasGiveableEffect() && event.getPlayer().hasPotionEffect(perks.getPotionEffectType())) {
                event.getPlayer().removePotionEffect(perks.getPotionEffectType());
            }
        }
    }
}
