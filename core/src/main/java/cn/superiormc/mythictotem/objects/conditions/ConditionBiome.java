package cn.superiormc.mythictotem.objects.conditions;

import cn.superiormc.mythictotem.objects.singlethings.BonusTotemData;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import cn.superiormc.mythictotem.objects.singlethings.TotemActiveData;
import org.bukkit.entity.Player;

public class ConditionBiome extends AbstractCheckCondition {

    public ConditionBiome() {
        super("biome");
        setRequiredArgs("biome");
        setRequirePlayer(false);
    }

    @Override
    protected boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, AbstractThingData thingData) {
        if (player == null || singleCondition.getBoolean("block-as-trigger", false)) {
            if (thingData instanceof TotemActiveData totemActiveData) {
                return totemActiveData.check.getBlock().getBiome().name().equals(singleCondition.getString("biome").toUpperCase());
            } else if (thingData instanceof BonusTotemData bonusTotemData) {
                return bonusTotemData.location.getBlock().getBiome().name().equals(singleCondition.getString("biome").toUpperCase());
            }
        }
        return player.getLocation().getBlock().getBiome().name().equals(singleCondition.getString("biome").toUpperCase());
    }
}
