package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public class ActionConsoleCommand extends AbstractRunAction {

    public ActionConsoleCommand() {
        super("console_command");
        setRequiredArgs("command");
        setRequirePlayer(false);
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        MythicTotem.methodUtil.dispatchCommand(singleAction.getString("command", player, thingData));
    }
}
