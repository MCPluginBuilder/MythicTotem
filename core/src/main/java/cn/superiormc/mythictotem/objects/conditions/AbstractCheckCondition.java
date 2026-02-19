package cn.superiormc.mythictotem.objects.conditions;

import cn.superiormc.mythictotem.managers.ErrorManager;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public abstract class AbstractCheckCondition {

    private final String type;

    private String[] requiredArgs;

    private boolean requirePlayer = true;

    public AbstractCheckCondition(String type) {
        this.type = type;
    }

    protected void setRequiredArgs(String... requiredArgs) {
        this.requiredArgs = requiredArgs;
    }

    protected void setRequirePlayer(boolean b) {
        this.requirePlayer = b;
    }

    public boolean checkCondition(ObjectSingleCondition singleCondition, Player player, AbstractThingData thingData) {
        if (player == null && requirePlayer) {
            return false;
        }
        if (requiredArgs != null) {
            for (String arg : requiredArgs) {
                if (!singleCondition.getSection().contains(arg)) {
                    ErrorManager.errorManager.sendErrorMessage("§cError: Your condition missing required arg: " + arg + ".");
                    return true;
                }
            }
        }
        return onCheckCondition(singleCondition, player, thingData);
    }

    protected abstract boolean onCheckCondition(ObjectSingleCondition singleCondition, Player player, AbstractThingData thingData);

    public String getType() {
        return type;
    }
}
