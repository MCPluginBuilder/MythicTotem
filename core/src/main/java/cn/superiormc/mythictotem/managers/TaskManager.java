package cn.superiormc.mythictotem.managers;

import cn.superiormc.mythictotem.MythicTotem;
import cn.superiormc.mythictotem.utils.SchedulerUtil;

public class TaskManager {

    public static TaskManager taskManager;

    private SchedulerUtil bonusEffectsTask;

    public TaskManager() {
        taskManager = this;
        if (!MythicTotem.isFolia && ConfigManager.configManager.getBoolean("bonus-effects.enabled", false)) {
            initBonusEffectsTasks();
        }
    }

    public void initBonusEffectsTasks() {
        bonusEffectsTask = SchedulerUtil.runTaskTimer(
                    () -> BonusEffectsManager.manager.tick(),
                    20L,
                    1L
        );
    }

    public void cancelTask() {
        if (bonusEffectsTask != null) {
            bonusEffectsTask.cancel();
        }
    }
}
