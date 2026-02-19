package cn.superiormc.mythictotem.objects.conditions;

import cn.superiormc.mythictotem.managers.ConditionManager;
import cn.superiormc.mythictotem.objects.AbstractSingleRun;
import cn.superiormc.mythictotem.objects.ObjectCondition;
import cn.superiormc.mythictotem.objects.checks.ObjectCheck;
import cn.superiormc.mythictotem.objects.checks.ObjectPlaceCheck;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import cn.superiormc.mythictotem.objects.singlethings.TotemActiveData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ObjectSingleCondition extends AbstractSingleRun {

    private final ObjectCondition condition;

    public ObjectSingleCondition(ObjectCondition condition, ConfigurationSection conditionSection) {
        super(conditionSection);
        this.condition = condition;
    }

    public boolean checkBoolean(Player player, AbstractThingData thingData) {
        if (thingData instanceof TotemActiveData totemActiveData) {
            if (totemActiveData.check.getItem() == null) {
                return false;
            }
        }
        return ConditionManager.conditionManager.checkBoolean(this, player, thingData);
    }

    public ObjectCondition getCondition() {
        return condition;
    }

}
