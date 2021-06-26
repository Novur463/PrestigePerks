package com.minehorizon.prestigeperks.listener.perks.potion;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionRespawn implements Listener {
    private final PrestigePerks prestigePerks;

    public PotionRespawn(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        for (Perks perks : Perks.values()) {
            if (event.getPlayer().hasPermission(perks.getPermission())) {
                if (perks.hasGiveableEffect()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(event.getPlayer().hasPotionEffect(perks.getPotionEffectType())) {
                                event.getPlayer().removePotionEffect(perks.getPotionEffectType());
                            }
                            event.getPlayer().addPotionEffect(new PotionEffect(perks.getPotionEffectType(), Integer.MAX_VALUE, perks.getPower()));
                        }
                    }.runTaskLater(prestigePerks,1L);
                }
            }
        }
    }
}
