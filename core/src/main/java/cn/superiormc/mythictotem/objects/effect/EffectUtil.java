package cn.superiormc.mythictotem.objects.effect;

import cn.superiormc.mythictotem.managers.ConfigManager;
import cn.superiormc.mythictotem.objects.ObjectCondition;
import cn.superiormc.mythictotem.objects.ObjectTotem;
import cn.superiormc.mythictotem.objects.singlethings.BonusTotemData;
import cn.superiormc.mythictotem.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EffectUtil {

    public static EffectStatus startEffect(Player player, BonusTotemData data) {
        EffectStatus effectStatus = new EffectStatus();
        ConfigurationSection section = data.getSection().getConfigurationSection("effects");
        if (section != null) {
            for (String tempVal1 : section.getKeys(false)) {
                if (tempVal1.equals("enabled")) {
                    continue;
                }
                ConfigurationSection tempVal3 = section.getConfigurationSection(tempVal1);
                if (tempVal3 == null) {
                    continue;
                }
                AbstractEffect tempVal2 = null;
                switch (tempVal3.getString("type", "MythicLib")) {
                    case "MythicLib":
                        if (CommonUtil.checkPluginLoad("MythicLib")) {
                            tempVal2 = new ObjectMMOEffect(data.totemUUID + tempVal1,
                                    player,
                                    tempVal3, data);
                        }
                        break;
                    case "MythicMobs":
                        if (CommonUtil.checkPluginLoad("MythicMobs")) {
                            tempVal2 = new ObjectMMEffect(data.totemUUID + tempVal1,
                                    player,
                                    tempVal3, data);
                        }
                        break;
                    case "AuraSkills":
                        if (CommonUtil.checkPluginLoad("AuraSkills")) {
                            tempVal2 = new ObjectAuraSkillsEffect(data.totemUUID + tempVal1,
                                    player,
                                    tempVal3, data);
                        }
                        break;
                }
                if (tempVal2 != null) {
                    if (tempVal2.getCondition().getAllBoolean(player, data)) {
                        tempVal2.addPlayerStat();
                        effectStatus.addAcvtiedEffects(tempVal2);
                    } else {
                        effectStatus.addNotActivedEffects(tempVal2);
                    }
                }
            }
        }
        return effectStatus;
    }

    public static int getMaxEffectsAmount(Player player, BonusTotemData data) {
        if (!ConfigManager.configManager.getBoolean("bonus-effects.limit.enabled", true) || data.totem == null) {
            return Integer.MAX_VALUE;
        }
        String groupID = data.totem.getSection().getString("bonus-effects.group", null);
        ConfigurationSection section;
        if (groupID == null) {
            section = ConfigManager.configManager.getConfigurationSection("bonus-effects.limit.value.default");
            if (section == null) {
                section = ConfigManager.configManager.getConfigurationSection("bonus-effects.limit.value");
                if (section == null) {
                    return Integer.MAX_VALUE;
                }
            }
        } else {
            section = ConfigManager.configManager.getConfigurationSection("bonus-effects.limit.value" + groupID);
            if (section == null) {
                return Integer.MAX_VALUE;
            }
        }
        ConfigurationSection conditionSection = ConfigManager.configManager.getConfigurationSection("bonus-effects.limit.conditions");
        if (conditionSection == null) {
            return section.getInt("default", Integer.MAX_VALUE);
        }
        Set<String> groupNameSet = conditionSection.getKeys(false);
        List<Integer> result = new ArrayList<>();
        for (String groupName : groupNameSet) {
            ObjectCondition condition = new ObjectCondition(conditionSection.getConfigurationSection(groupName));
            if (section.getInt(groupName, 0) > 0 && condition.getAllBoolean(player, data)) {
                result.add(section.getInt(groupName));
            }
            else {
                if (section.getInt("default") > 0) {
                    result.add(section.getInt("default", 1));
                }
            }
        }
        if (result.isEmpty()) {
            result.add(1);
        }
        return Collections.max(result);
    }

}
