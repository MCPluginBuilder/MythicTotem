package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public class ActionTitle extends AbstractRunAction {

    public ActionTitle() {
        super("title");
        setRequiredArgs("main-title", "sub-title", "fade-in", "stay", "fade-out");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        MythicTotem.methodUtil.sendTitle(player,
                singleAction.getString("main-title", player, thingData),
                singleAction.getString("sub-title", player, thingData),
                singleAction.getInt("fade-in"),
                singleAction.getInt("stay"),
                singleAction.getInt("fade-out"));
    }
}
