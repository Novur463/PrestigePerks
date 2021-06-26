package com.minehorizon.prestigeperks.util;

import com.minehorizon.prestigeperks.PrestigePerks;
import com.minehorizon.prestigeperks.util.handler.Perks;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    private final PrestigePerks prestigePerks;
    public Utility(PrestigePerks prestigePerks) {
        this.prestigePerks = prestigePerks;
    }

    public void noPermission(Player player) {
        player.sendMessage(color(prestigePerks.getConfig().getString("messages.noPermission")));
    }

    public boolean hasPerk(Perks perks, Player player) {
        return player.hasPermission(perks.getPermission());
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public List<String> color(List<String> stringList) {
        List<String> colorList = new ArrayList<>();
        for(String s : stringList) {
            colorList.add(color(s));
        }
        return colorList;
    }

    public boolean isFortuneApplicable(Block block) {
        return prestigePerks.getFortuneableBlocks().contains(block.getType());
    }
}
