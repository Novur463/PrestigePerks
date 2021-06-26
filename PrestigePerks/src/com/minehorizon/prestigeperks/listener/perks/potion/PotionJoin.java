package com.minehorizon.prestigeperks.listener.perks.potion;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

public class PotionJoin implements Listener {
    private final PrestigePerks prestigePerks;
    public PotionJoin(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        for(Perks perks : Perks.values()) {
            if(event.getPlayer().hasPermission(perks.getPermission())) {
                Player player = event.getPlayer();
                if (perks.hasGiveableEffect()) {
                    if(perks.hasGiveableEffect()) {
                        if(player.hasPotionEffect(perks.getPotionEffectType())) {
                            player.removePotionEffect(perks.getPotionEffectType());
                        }
                        player.addPotionEffect(new PotionEffect(perks.getPotionEffectType(), Integer.MAX_VALUE, perks.getPower()));
                    }
                }
            }
        }
    }
}
