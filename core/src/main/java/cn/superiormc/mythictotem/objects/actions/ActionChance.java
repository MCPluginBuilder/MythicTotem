package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.ObjectAction;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ActionChance extends AbstractRunAction {

    public ActionChance() {
        super("chance");
        setRequiredArgs("rate", "actions");
        setRequirePlayer(false);
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        if (MythicTotem.freeVersion) {
            return;
        }
        ConfigurationSection chanceSection = singleAction.getSection().getConfigurationSection("actions");
        if (chanceSection == null) {
            return;
        }
        double rate = singleAction.getDouble("rate", player, thingData);
        if (RandomUtils.nextDouble(0, 100) <= rate) {
            ObjectAction action = new ObjectAction(chanceSection);
            action.runAllActions(player, thingData);
        }
    }
}
