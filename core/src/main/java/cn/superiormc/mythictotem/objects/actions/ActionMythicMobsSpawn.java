package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.objects.singlethings.BonusTotemData;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import cn.superiormc.mythictotem.objects.singlethings.TotemActiveData;
import cn.superiormc.mythictotem.utils.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ActionMythicMobsSpawn extends AbstractRunAction {

    public ActionMythicMobsSpawn() {
        super("mythicmobs_spawn");
        setRequiredArgs("entity");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        String mobName = singleAction.getString("entity");
        String worldName = singleAction.getString("world");
        Location location = null;
        if (player == null || singleAction.getBoolean("block-as-trigger", false)) {
            if (thingData instanceof TotemActiveData totemActiveData) {
                location = totemActiveData.check.getBlock().getLocation();
            } else if (thingData instanceof BonusTotemData bonusTotemData) {
                location = bonusTotemData.location;
            }
        } else if (worldName == null) {
            location = player.getLocation();
        } else {
            World world = Bukkit.getWorld(worldName);
            location = new Location(world,
                    singleAction.getDouble("x", player, thingData),
                    singleAction.getDouble("y", player, thingData),
                    singleAction.getDouble("z", player, thingData));

        }
        if (location != null) {
            CommonUtil.summonMythicMobs(location, mobName, singleAction.getInt("level", 1));
        }
    }
}
