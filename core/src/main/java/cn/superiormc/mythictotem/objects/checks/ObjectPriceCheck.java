package cn.superiormc.mythictotem.objects.checks;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.hooks.ItemPriceUtil;
import cn.superiormc.mythictotem.managers.ConfigManager;
import cn.superiormc.mythictotem.managers.ErrorManager;
import cn.superiormc.mythictotem.managers.HookManager;
import cn.superiormc.mythictotem.methods.BuildItem;
import cn.superiormc.mythictotem.utils.CommonUtil;
import cn.superiormc.mythictotem.utils.MathUtil;
import cn.superiormc.mythictotem.utils.SchedulerUtil;
import cn.superiormc.mythictotem.utils.TextUtil;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ObjectPriceCheck {

    private final ConfigurationSection section;

    private final Player player;

    private final Block block;

    private final String type;

    private final double cost;

    public ObjectPriceCheck(ConfigurationSection section, Player player, Block block) {
        this.section = section;
        this.player = player;
        this.block = block;
        if (section == null) {
            type = "unknown";
            this.cost = 0;
            return;
        } else if (section.contains("hook-plugin") && section.contains("hook-item")) {
            type = "hook";
        } else if (section.contains("match-item") && CommonUtil.checkPluginLoad("MythicChanger")) {
            type = "match";
        } else if (section.contains("material")) {
            type = "vanilla";
        } else if (section.contains("economy-plugin")) {
            type = "economy";
        } else if (section.contains("economy-type") && !(section.contains("economy-plugin"))) {
            type = "exp";
        } else if (section.getBoolean("block-as-price")) {
            type = "block";
        } else {
            type = "free";
        }
        this.cost = MathUtil.doCalculate(section.getString("amount", "1"));
        if (ConfigManager.configManager.getBoolean("debug", false)) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §aPrice Type: " + type + "!");
        }
    }

    public boolean checkPrice(boolean take, ItemStack keyItems) {
        if (ConfigManager.configManager.getBoolean("debug", false)) {
            TextUtil.sendMessage(null, TextUtil.pluginPrefix() + " §aKey Item: " + keyItems + "!");
        }
        if (MythicTotem.freeVersion && !type.equals("free")) {
            ErrorManager.errorManager.sendErrorMessage("§cError: Free version can not use price feature. This price check is skipped.");
            return false;
        }
        boolean priceBoolean = false;
        switch (type) {
            case "free":
                priceBoolean = true;
                break;
            case "block":
                SchedulerUtil.runSync(() -> {
                    CommonUtil.removeBlock(block);
                });
                break;
            case "hook":
                priceBoolean = ItemPriceUtil.getPrice(section.getString("hook-plugin"),
                        section.getString("hook-item"),
                        player,
                        (int) cost, take, keyItems);
                break;
            case "match":
                priceBoolean = ItemPriceUtil.getPrice(player, section, (int) cost, take);
                break;
            case "vanilla":
                priceBoolean = ItemPriceUtil.getPrice(player,
                        BuildItem.buildItemStack(player, section, 1),
                        (int) cost,
                        take, keyItems);
                break;
            case "economy":
                priceBoolean = HookManager.hookManager.getPrice(player, section.getString("economy-plugin"),
                        section.getString("economy-type", "default"),
                        cost, take);
                break;
            case "exp":
                priceBoolean = HookManager.hookManager.getPrice(player, section.getString("economy-type"),
                        (int) cost, take);
                break;
            case "unknwon":
                ErrorManager.errorManager.sendErrorMessage("§cError: There is something wrong in your totem configs!");
                break;
        }
        return priceBoolean;
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public double getCost() {
        return cost;
    }
}
