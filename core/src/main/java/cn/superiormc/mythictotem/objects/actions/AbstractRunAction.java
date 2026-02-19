package cn.superiormc.mythictotem.objects.actions;


import cn.superiormc.mythictotem.managers.ErrorManager;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import org.bukkit.entity.Player;

public abstract class AbstractRunAction {

    private final String type;

    private String[] requiredArgs;

    private boolean requirePlayer = true;

    public AbstractRunAction(String type) {
        this.type = type;
    }

    protected void setRequiredArgs(String... requiredArgs) {
        this.requiredArgs = requiredArgs;
    }

    protected void setRequirePlayer(boolean b) {
        this.requirePlayer = b;
    }

    public void runAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        if (player == null && requirePlayer) {
            return;
        }
        if (requiredArgs != null) {
            for (String arg : requiredArgs) {
                if (!singleAction.getSection().contains(arg)) {
                    ErrorManager.errorManager.sendErrorMessage("§cError: Your action missing required arg: " + arg + ".");
                    return;
                }
            }
        }
        onDoAction(singleAction, player, thingData);
    }

    protected abstract void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData);

    public String getType() {
        return type;
    }
}
