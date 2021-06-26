package com.minehorizon.prestigeperks.listener.perks.autopickup;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AutoEnable implements Listener {
    private final PrestigePerks prestigePerks;
    public AutoEnable(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission(Perks.AUTOPICKUP.getPermission())) {
            if(!prestigePerks.getToggledAutoPickup().contains(event.getPlayer().getUniqueId())) {
                prestigePerks.getToggledAutoPickup().add(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.apAutoEnabled")));
            }
        }

        if(event.getPlayer().hasPermission(Perks.AUTOSMELT.getPermission())) {
            if(!prestigePerks.getToggledAutoSmelt().contains(event.getPlayer().getUniqueId())) {
                prestigePerks.getToggledAutoSmelt().add(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.asAutoEnabled")));
            }
        }
    }
}
