package cn.superiormc.mythictotem.objects.effect;

import cn.superiormc.mythictotem.objects.AbstractSingleRun;
import cn.superiormc.mythictotem.objects.ObjectCondition;
import cn.superiormc.mythictotem.objects.singlethings.BonusTotemData;
import cn.superiormc.mythictotem.utils.SchedulerUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public abstract class AbstractEffect extends AbstractSingleRun {

    protected Player player;

    protected final String id;

    protected final ObjectCondition condition;

    protected final boolean alwaysCheckCondition;

    protected int retryTimes = 0;

    protected SchedulerUtil retryTask;

    protected BonusTotemData data;

    public AbstractEffect(String id, Player player, ConfigurationSection section, BonusTotemData data) {
        super(section);
        this.id = id;
        this.player = player;
        this.section = section;
        this.condition = new ObjectCondition(section.getConfigurationSection("conditions"));
        this.alwaysCheckCondition = section.getBoolean("bypass-condition-after-apply");
        this.data = data;
    }

    public abstract void addPlayerStat();

    public abstract void removePlayerStat();

    public ObjectCondition getCondition() {
        return condition;
    }

    public boolean isAlwaysCheckCondition() {
        return alwaysCheckCondition;
    }
}
