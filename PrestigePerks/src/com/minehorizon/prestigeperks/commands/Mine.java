package com.minehorizon.prestigeperks.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Mine implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof ConsoleCommandSender) {
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
            String activeCommand = getActiveWarpCommand(player);

            if(activeCommand != null) {
                Bukkit.dispatchCommand(player,activeCommand);
                return true;
            }
        }

        if(args.length == 1) {
            if(!isRegisteredWarpAlias(args[0])) {
                player.sendMessage(args[0] + " is not a registered warp alias");
                return true;
            }

            WarpLocations warpLocations = get(args[0]);
            if(warpLocations != null) {
                Bukkit.dispatchCommand(player,warpLocations.getLinkedCommand());
                return true;
            }
        }

        return true;
    }

    public String getActiveWarpCommand(Player player) {
        for(WarpLocations warpLocations : WarpLocations.values()) {
            if(player.hasPermission(warpLocations.getActivePermission())) {
                return warpLocations.getLinkedCommand();
            }
        }
        return null;
    }

    private WarpLocations get(String string) {
        for(WarpLocations warpLocations : WarpLocations.values()) {
            for(String aliases : warpLocations.getAlias()) {
                if(aliases.toLowerCase().equalsIgnoreCase(string.toLowerCase())) {
                    return warpLocations;
                }
            }
        }
        return null;
    }

    private boolean isRegisteredWarpAlias(String string) {
        for(WarpLocations warpLocations : WarpLocations.values()) {
            for(String s : warpLocations.getAlias()) {
                if(s.toLowerCase().equalsIgnoreCase(string.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getWarpLocation(Player player) {
        for(WarpLocations warpLocations : WarpLocations.values()) {
            if(warpLocations.hasPermission()) {
                if (player.hasPermission(warpLocations.getPermission())) {
                    return warpLocations.getLinkedCommand();
                }
            }
        }
        return null;
    }

    public enum WarpLocations {
        INFIRMARY(true,"warps.infirmary","warps.active.infirmary", "warp InfirmaryMine", "infirmary", "inf", "iy"),
        GENPOP(true, "warps.genpop", "warps.active.genpop", "warp GenpopMine", "genpop", "gp", "gen"),
        COURTYARD(true, "warps.courtyard", "warps.active.courtyard", "warp CourtyardMine", "courtyard", "court", "cy"),
        CHAPEL(true, "warps.chapel", "warps.active.chapel", "warp ChapelMine", "chapel", "chap");

        boolean hasPermission;
        String permission, linkedCommand, activePermission;
        String[] alias;

        WarpLocations(boolean hasPermission, String permission, String activePermission, String linkedCommand,  String... alias) {
            this.hasPermission = hasPermission;
            this.permission = permission;
            this.activePermission = activePermission;
            this.linkedCommand = linkedCommand;
            this.alias = alias;
        }

        public boolean hasPermission() {
            return hasPermission;
        }

        public String getPermission() {
            return permission;
        }

        public String getLinkedCommand() {
            return linkedCommand;
        }

        public String[] getAlias() {
            return alias;
        }

        public String getActivePermission() {
            return activePermission;
        }
    }
}
