package com.minehorizon.prestigeperks.perkgui;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GUICommand implements CommandExecutor {
    private final PrestigePerks prestigePerks;
    public GUICommand(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            GUIHandler guiHandler = new GUIHandler(prestigePerks,player);

            guiHandler.openInv();
        }
        return true;
    }
}
