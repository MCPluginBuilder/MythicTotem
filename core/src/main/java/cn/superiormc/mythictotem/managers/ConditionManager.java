package cn.superiormc.mythictotem.managers;

import cn.superiormc.mythictotem.objects.conditions.*;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ConditionManager {

    public static ConditionManager conditionManager;

    private Map<String, AbstractCheckCondition> conditions;

    public ConditionManager() {
        conditionManager = this;
        initConditions();
    }

    private void initConditions() {
        conditions = new HashMap<>();
        registerNewCondition("biome", new ConditionBiome());
        registerNewCondition("permission", new ConditionPermission());
        registerNewCondition("placeholder", new ConditionPlaceholder());
        registerNewCondition("world", new ConditionWorld());
        registerNewCondition("any", new ConditionAny());
        registerNewCondition("mobs_near", new ConditionMobsNear());
        registerNewCondition("trigger", new ConditionTrigger());
        registerNewCondition("trigger_item", new ConditionTriggerItem());
    }

    public void registerNewCondition(String actionID,
                                  AbstractCheckCondition condition) {
        if (!conditions.containsKey(actionID)) {
            conditions.put(actionID, condition);
        }
    }

    public boolean checkBoolean(ObjectSingleCondition condition, Player player, AbstractThingData thingData) {
        for (AbstractCheckCondition checkCondition : conditions.values()) {
            String type = condition.getString("type");
            if (checkCondition.getType().equals(type) && !checkCondition.checkCondition(condition, player, thingData)) {
                return false;
            }
        }
        return true;
    }
}
