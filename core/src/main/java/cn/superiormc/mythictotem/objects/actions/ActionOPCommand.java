package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public class ActionOPCommand extends AbstractRunAction {

    public ActionOPCommand() {
        super("op_command");
        setRequiredArgs("command");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        MythicTotem.methodUtil.dispatchOpCommand(player, singleAction.getString("command", player, thingData));
    }
}
