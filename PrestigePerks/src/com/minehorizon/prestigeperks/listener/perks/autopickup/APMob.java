package com.minehorizon.prestigeperks.listener.perks.autopickup;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class APMob implements Listener {
    private final PrestigePerks prestigePerks;
    public APMob(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            if(event.getEntity().getKiller() != null) {
                if(prestigePerks.getToggledAutoPickup().contains(event.getEntity().getKiller().getUniqueId())) {
                    event.getDrops().forEach(itemStack -> event.getEntity().getKiller().getInventory().addItem(itemStack));
                    event.getDrops().clear();
                }
            }
        }
    }
}
