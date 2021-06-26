package com.minehorizon.prestigeperks.pickaxes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.swing.*;

public class MazePickaxe /*implements CommandExecutor, Listener */{


    /*@EventHandler
    public void onMine(BlockBreakEvent event) {

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        //potionMeta.addCustomEffect(PotionEffectType.SPEED, true, 1);

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();

        ItemStack mazePickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta mazePickaxemeta = mazePickaxe.getItemMeta();
        mazePickaxe.setItemMeta(mazePickaxemeta);

        if(Bukkit.getOfflinePlayer("test") == null) {
            
        }


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        return false;
    }*/
}
