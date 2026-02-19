package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.managers.ActionManager;
import cn.superiormc.mythictotem.objects.AbstractSingleRun;
import cn.superiormc.mythictotem.objects.ObjectAction;
import cn.superiormc.mythictotem.objects.checks.ObjectCheck;
import cn.superiormc.mythictotem.objects.checks.ObjectPlaceCheck;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import cn.superiormc.mythictotem.objects.singlethings.TotemActiveData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ObjectSingleAction extends AbstractSingleRun {

    private final ObjectAction action;


    public ObjectSingleAction(ObjectAction action, ConfigurationSection actionSection) {
        super(actionSection);
        this.action = action;
    }

    public void doAction(Player player, AbstractThingData thingData) {
        ActionManager.actionManager.doAction(this, player, thingData);
    }


    public ObjectAction getAction() {
        return action;
    }

}
