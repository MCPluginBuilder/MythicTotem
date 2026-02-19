package cn.superiormc.mythictotem.objects.conditions;


import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public class ConditionPermission extends AbstractCheckCondition {

    public ConditionPermission() {
        super("permission");
        setRequiredArgs("permission");
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, AbstractThingData thingData) {
        return player.hasPermission(singleCondition.getString("permission"));
    }
}
