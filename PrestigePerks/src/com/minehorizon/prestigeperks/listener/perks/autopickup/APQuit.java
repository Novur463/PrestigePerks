package com.minehorizon.prestigeperks.listener.perks.autopickup;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class APQuit implements Listener {
    private final PrestigePerks prestigePerks;
    public APQuit(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if((prestigePerks.getToggledAutoPickup().contains(event.getPlayer().getUniqueId()))) {
            prestigePerks.getToggledAutoPickup().remove(event.getPlayer().getUniqueId());
        }
    }
}
