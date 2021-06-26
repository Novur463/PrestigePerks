package com.minehorizon.prestigeperks;

import com.minehorizon.prestigeperks.commands.GiveToken;
import com.minehorizon.prestigeperks.commands.Mine;
import com.minehorizon.prestigeperks.commands.ToggleAS;
import com.minehorizon.prestigeperks.commands.ToggleDrops;
import com.minehorizon.prestigeperks.listener.ClickToken;
import com.minehorizon.prestigeperks.listener.perks.BlockEvents;
import com.minehorizon.prestigeperks.listener.perks.HeadHunter;
import com.minehorizon.prestigeperks.listener.perks.Satiated;
import com.minehorizon.prestigeperks.listener.perks.autopickup.APMob;
import com.minehorizon.prestigeperks.listener.perks.autopickup.APQuit;
import com.minehorizon.prestigeperks.listener.perks.autopickup.AutoEnable;
import com.minehorizon.prestigeperks.listener.perks.potion.PotionJoin;
import com.minehorizon.prestigeperks.listener.perks.potion.PotionQuit;
import com.minehorizon.prestigeperks.listener.perks.potion.cl.CLTag;
import com.minehorizon.prestigeperks.listener.perks.potion.cl.CLUntag;
import com.minehorizon.prestigeperks.listener.perks.potion.PotionRespawn;
import com.minehorizon.prestigeperks.perkgui.GUICommand;
import com.minehorizon.prestigeperks.perkgui.GUIInteract;
import com.minehorizon.prestigeperks.util.FortuneUtil;
import com.minehorizon.prestigeperks.util.ItemUtil;
import com.minehorizon.prestigeperks.util.Utility;
import com.minehorizon.prestigeperks.util.handler.Perks;
import com.minehorizon.prestigeperks.util.handler.TokenHandler;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PrestigePerks extends JavaPlugin {
    private PrestigePerks instance;

    private Map<String, ItemStack> registeredPerkItems;

    private List<UUID> toggledAutoPickup;
    private List<UUID> toggledAutoSmelt;

    private List<Material> fortuneableBlocks;

    private Utility utility;
    private TokenHandler tokenHandler;
    private ItemUtil itemUtil;
    private FortuneUtil fortuneUtil;

    @Override
    public void onEnable() {

        if(!combatLogEnabled()) {
            getServer().getPluginManager().disablePlugin(this);
            System.out.println("[ERROR] PrestigePerks disabled, no CombatLogX plugin found..");
        }

        instance = this;

        utility = new Utility(instance);
        tokenHandler = new TokenHandler(instance);
        itemUtil = new ItemUtil(instance);
        fortuneUtil = new FortuneUtil(instance);

        registerCommands("perks", new GUICommand(instance));
        registerCommands("prestigeperk", new GiveToken(instance));
        registerCommands("autopickup", new ToggleDrops(instance));
        registerCommands("autosmelt", new ToggleAS(instance));

        getServer().getPluginManager().registerEvents(new GUIInteract(instance), this);
        getServer().getPluginManager().registerEvents(new ClickToken(instance), this);

        //CombatLog
        getServer().getPluginManager().registerEvents(new CLTag(instance),this);
        getServer().getPluginManager().registerEvents(new CLUntag(instance), this);
        getServer().getPluginManager().registerEvents(new PotionRespawn(instance), this);
        getServer().getPluginManager().registerEvents(new PotionJoin(instance), this);
        getServer().getPluginManager().registerEvents(new PotionQuit(instance), this);

        //Perks
        getServer().getPluginManager().registerEvents(new Satiated(instance), this);
        getServer().getPluginManager().registerEvents(new HeadHunter(instance), this);
        if(autoEnablePerks()) {
            getServer().getPluginManager().registerEvents(new AutoEnable(instance), this);
        }
        getServer().getPluginManager().registerEvents(new APQuit(instance), this);

        getServer().getPluginManager().registerEvents(new BlockEvents(instance), this);
        getServer().getPluginManager().registerEvents(new APMob(instance), this);

        toggledAutoPickup = new ArrayList<>();
        toggledAutoSmelt = new ArrayList<>();
        fortuneableBlocks = new ArrayList<>();
        registeredPerkItems = new HashMap<>();
        fill();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private boolean combatLogEnabled() {
        return getServer().getPluginManager().isPluginEnabled("CombatLogX");
    }

    private void fill() {
        for(Perks perks : Perks.values()) {
            registeredPerkItems.put(perks.getConfigIdentifier(), itemUtil.createPerkItem(perks.getConfigIdentifier()));
        }

        for(String string : getConfig().getStringList("fortuneableMaterials")) {
            fortuneableBlocks.add(Material.matchMaterial(string));
        }
    }

    public boolean isInApplicableRegion(Block block) {
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(block.getWorld()));
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(block.getLocation()));
        for(ProtectedRegion protectedRegion : applicableRegionSet) {
            if(getConfig().getStringList("applicableRegions").contains(protectedRegion.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInWoodFarm(Block block) {
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(block.getWorld()));
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(block.getLocation()));
        for(ProtectedRegion protectedRegion : applicableRegionSet) {
            if(getConfig().getStringList("woodRegions").contains(protectedRegion.getId())) {
                return true;
            }
        }
        return false;
    }

    public List<Material> getFortuneableBlocks() {
        return fortuneableBlocks;
    }

    public List<UUID> getToggledAutoSmelt() {
        return toggledAutoSmelt;
    }

    public List<UUID> getToggledAutoPickup() {
        return toggledAutoPickup;
    }

    public boolean autoEnablePerks() {
        return getConfig().getBoolean("autoEnablePerks");
    }

    public Map<String, ItemStack> getRegisteredPerkMats() {
        return registeredPerkItems;
    }

    public FortuneUtil getFortuneUtil() {
        return fortuneUtil;
    }

    public ItemUtil getItemUtil() {
        return itemUtil;
    }

    public TokenHandler getTokenHandler() {
        return tokenHandler;
    }

    public Utility getUtility() {
        return utility;
    }

    private void registerCommands(String command, CommandExecutor executor) {
        PluginCommand pluginCommand = getCommand(command);
        if(pluginCommand == null) {
            return;
        }
        pluginCommand.setExecutor(executor);
    }
}
