package cn.superiormc.mythictotem.objects.effect;

import cn.superiormc.mythictotem.objects.ObjectTotem;
import cn.superiormc.mythictotem.objects.singlethings.BonusTotemData;
import cn.superiormc.mythictotem.utils.CommonUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class EffectUtil {

    public static EffectStatus startEffect(ObjectTotem totem, Player player, BonusTotemData data) {
        EffectStatus effectStatus = new EffectStatus();
        ConfigurationSection section = totem.getSection().getConfigurationSection("bonus-effects.effects");
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
                            tempVal2 = new ObjectMMOEffect(totem.getTotemID() + tempVal1,
                                    player,
                                    tempVal3, data);
                        }
                        break;
                    case "MythicMobs":
                        if (CommonUtil.checkPluginLoad("MythicMobs")) {
                            tempVal2 = new ObjectMMEffect(totem.getTotemID() + tempVal1,
                                    player,
                                    tempVal3, data);
                        }
                        break;
                    case "AuraSkills":
                        if (CommonUtil.checkPluginLoad("AuraSkills")) {
                            tempVal2 = new ObjectAuraSkillsEffect(totem.getTotemID() + tempVal1,
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

}
