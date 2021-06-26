package com.minehorizon.prestigeperks.commands;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleDrops implements CommandExecutor {
    private final PrestigePerks prestigePerks;
    public ToggleDrops(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player)sender;

            if(!player.hasPermission(Perks.AUTOPICKUP.getPermission())) {
                prestigePerks.getUtility().noPermission(player);
                return true;
            }

            if(prestigePerks.getToggledAutoPickup().contains(player.getUniqueId())) {
                prestigePerks.getToggledAutoPickup().remove(player.getUniqueId());
                player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.autoPickupDisabled")));
                return true;
            }

            prestigePerks.getToggledAutoPickup().add(player.getUniqueId());
            player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.autoPickupEnabled")));
            return true;
        }
        return true;
    }
}
