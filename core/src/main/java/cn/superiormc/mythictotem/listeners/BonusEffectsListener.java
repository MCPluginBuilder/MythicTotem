package cn.superiormc.mythictotem.listeners;

import cn.superiormc.mythictotem.managers.BonusEffectsManager;
import cn.superiormc.mythictotem.objects.effect.ObjectAuraSkillsEffect;
import cn.superiormc.mythictotem.utils.CommonUtil;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BonusEffectsListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        BonusEffectsManager.manager.destroyTotem(block.getLocation());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            BonusEffectsManager.manager.destroyTotem(block.getLocation());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            BonusEffectsManager.manager.destroyTotem(block.getLocation());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            BonusEffectsManager.manager.destroyTotem(block.getLocation());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks()) {
            BonusEffectsManager.manager.destroyTotem(block.getLocation());
        }
    }

    @EventHandler
    public void onEntityGrief(EntityChangeBlockEvent event) {
        Block block = event.getBlock();
        BonusEffectsManager.manager.destroyTotem(block.getLocation());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (CommonUtil.checkPluginLoad("AuraSkills")) {
            ObjectAuraSkillsEffect.removePlayerStat(event.getPlayer(), 1);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        BonusEffectsManager.manager.removePlayer(event.getPlayer());
    }
}
