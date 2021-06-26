package com.minehorizon.prestigeperks.commands;

import com.minehorizon.prestigeperks.PrestigePerks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveToken implements CommandExecutor {
    private final PrestigePerks prestigePerks;
    public GiveToken(PrestigePerks prestigePerks) { this.prestigePerks = prestigePerks; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(!player.hasPermission("prestigeperks.givetoken")) {
                prestigePerks.getUtility().noPermission(player);
                return true;
            }

            if(args.length != 2) {
                player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.invalidArgs")));
                return true;
            }

            if(args[0].equalsIgnoreCase("givetoken") || (args[0].equalsIgnoreCase("give"))) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.invalidPlayer")));
                    return true;
                }

                if(prestigePerks.getTokenHandler().isFull(target)) {
                    player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.targetInventoryFull").replace("{target}", target.getName())));
                    target.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.inventoryFull")));
                    return true;
                }

                prestigePerks.getTokenHandler().giveToken(target);

                if(player.getName().equalsIgnoreCase(target.getName())) {
                    target.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.receivedToken")));
                    return true;
                }

                player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.gaveToken").replace("{player}", target.getName())));
                target.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.receivedToken")));
                return true;
            }

            player.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.invalidArgs")));
            return true;
        } else if(sender instanceof ConsoleCommandSender) {
            if(args.length != 2) {
                sender.sendMessage("Incorrect format! /prestigeperks give <player>");
                return true;
            }

            if(args[0].equalsIgnoreCase("givetoken") || (args[0].equalsIgnoreCase("give"))) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    sender.sendMessage("Error! Player cannot be found!");
                    return true;
                }

                if(prestigePerks.getTokenHandler().isFull(target)) {
                    sender.sendMessage(prestigePerks.getUtility().color("Target player " + target.getName() + " inventory is full!"));
                    target.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.inventoryFull")));
                    return true;
                }

                prestigePerks.getTokenHandler().giveToken(target);
                target.sendMessage(prestigePerks.getUtility().color(prestigePerks.getConfig().getString("messages.receivedToken")));
                sender.sendMessage("Player " + target.getName() + " given 1 prestige perk token");
                return true;
            }

            sender.sendMessage("Incorrect format! /prestigeperks give <player>");
            return true;
        }

        return true;
    }
}
