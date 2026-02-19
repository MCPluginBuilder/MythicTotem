package cn.superiormc.mythictotem.objects.conditions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.ObjectCondition;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ConditionAny extends AbstractCheckCondition {

    public ConditionAny() {
        super("any");
        setRequiredArgs("conditions");
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, AbstractThingData thingData) {
        if (MythicTotem.freeVersion) {
            return true;
        }
        ConfigurationSection anySection = singleCondition.getSection().getConfigurationSection("conditions");
        if (anySection == null) {
            return true;
        }
        ObjectCondition condition = new ObjectCondition(anySection);
        return condition.getAnyBoolean(player, thingData);
    }
}
