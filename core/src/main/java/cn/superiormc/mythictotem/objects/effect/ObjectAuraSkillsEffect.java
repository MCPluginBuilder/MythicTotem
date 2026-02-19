package cn.superiormc.mythictotem.objects.effect;

import cn.superiormc.mythictotem.managers.ErrorManager;
import cn.superiormc.mythictotem.objects.singlethings.BonusTotemData;
import cn.superiormc.mythictotem.utils.SchedulerUtil;
import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.registry.NamespacedId;
import dev.aurelium.auraskills.api.stat.Stat;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ObjectAuraSkillsEffect extends AbstractEffect {

    private final AuraSkillsApi auraSkills = AuraSkillsApi.get();

    public ObjectAuraSkillsEffect(String id, Player player, ConfigurationSection section, BonusTotemData data) {
        super(id, player, section, data);
        this.player = player;
    }

    @Override
    public void addPlayerStat() {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        if (user == null) {
            retryTimes ++;
            if (retryTimes < 3) {
                ErrorManager.errorManager.sendErrorMessage("§6Warning: Failed to add AuraSkills effect for player " + player.getName() + "," +
                        " don't worry, we will retry later. Retry Times: " + retryTimes + ".");
                SchedulerUtil.runTaskLater(this::addPlayerStat, 20L);
            } else {
                ErrorManager.errorManager.sendErrorMessage("§cError: Failed to add MythicMobs effect for player " + player.getName() + "," +
                        " if this always happen, try change cache.load-mode option to JOIN in config.yml file, if it only happens sometimes, just ignore this and ask" +
                        " this player equip the prefix again! This because effect plugin load data is slower than MythicTotem this times.");
            }
            return;
        }
        Stat stat = auraSkills.getGlobalRegistry().getStat(NamespacedId.fromDefault(section.getString("stat", "")));
        if (stat == null) {
            ErrorManager.errorManager.sendErrorMessage("§6Warning: Failed to add AuraSkills effect for player. Reason: Config Error");
            return;
        }
        user.addStatModifier(new StatModifier("MythicTotem_" + id, stat, getDouble("value", player, data)));
    }

    @Override
    public void removePlayerStat() {
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        if (user != null) {
            user.removeStatModifier("MythicTotem_" + id);
        }
    }

    public static void removePlayerStat(Player player, int times) {
        AuraSkillsApi auraSkills = AuraSkillsApi.get();
        SkillsUser user = auraSkills.getUser(player.getUniqueId());
        if (user != null && !user.getStatModifiers().isEmpty()) {
            for (String key : user.getStatModifiers().keySet()) {
                if (key.startsWith("MythicTotem_")) {
                    user.removeStatModifier(key);
                }
            }
        } else if (times < 3) {
            SchedulerUtil.runTaskLater(() -> removePlayerStat(player, times + 1), 60L);
        }
    }
}
