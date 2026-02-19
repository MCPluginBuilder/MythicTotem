package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.ObjectAction;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ActionAny extends AbstractRunAction {

    public ActionAny() {
        super("any");
        setRequiredArgs("actions");
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
        ObjectAction action = new ObjectAction(chanceSection);
        action.runRandomEveryActions(player, thingData, singleAction.getInt("amount", 1));
    }
}
