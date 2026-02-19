package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public class ActionActionBar extends AbstractRunAction {

    public ActionActionBar() {
        super("action_bar");
        setRequiredArgs("message");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        String msg = singleAction.getString("message", player, thingData);
        MythicTotem.methodUtil.sendActionBar(player, msg);
    }
}
