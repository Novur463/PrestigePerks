package com.minehorizon.prestigeperks.commands;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleAS implements CommandExecutor {
    private final PrestigePerks prestigePerks;
    public ToggleAS(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player)sender;

            if(player.hasPermission(Perks.AUTOSMELT.getPermission())) {

                if(prestigePerks.getToggledAutoSmelt().contains(player.getUniqueId())) {

                    prestigePerks.getToggledAutoSmelt().remove(player.getUniqueId());
                    player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.autosmeltDisabled")));
                    return true;

                } else {

                    prestigePerks.getToggledAutoSmelt().add(player.getUniqueId());
                    player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.autosmeltEnabled")));
                    return true;

                }

            }

            prestigePerks.getUtility().noPermission(player);
            return true;
        }
        return true;
    }
}
