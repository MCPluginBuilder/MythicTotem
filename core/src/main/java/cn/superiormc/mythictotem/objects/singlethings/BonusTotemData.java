package cn.superiormc.mythictotem.objects.singlethings;

import cn.superiormc.mythictotem.managers.ConfigManager;
import cn.superiormc.mythictotem.objects.ObjectTotem;
import org.bukkit.Location;

import java.util.UUID;

public class BonusTotemData extends AbstractThingData {

    public final Location location;

    public final int level;

    public final long placeTime;

    public final String totemId;

    public final boolean isCore;

    public final UUID totemUUID;

    public long lastCircleTime;

    public ObjectTotem totem;

    public BonusTotemData(Location location,
                          int level,
                          long placeTime,
                          String totemId,
                          boolean isCore,
                          UUID totemUUID) {

        this.location = location;
        this.level = level;
        this.placeTime = placeTime;
        this.totemId = totemId;
        this.isCore = isCore;
        this.totemUUID = totemUUID;
        this.totem = ConfigManager.configManager.getTotem(totemId);
    }

    public void setNewLastCircleTime() {
        this.lastCircleTime = System.currentTimeMillis();
    }

    public boolean canExecuteCircleActionAgain() {
        return lastCircleTime + totem.getSection().getLong("bonus-effects.period-ticks", 60) * 50 < System.currentTimeMillis();
    }
}