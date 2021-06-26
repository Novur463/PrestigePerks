package com.minehorizon.prestigeperks.listener.perks;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Satiated implements Listener {
    private PrestigePerks prestigePerks;
    public Satiated(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();

            if(player.hasPermission(Perks.NOFOODLOSS.getPermission())) {
                event.setCancelled(true);
            }
        }
    }
}
