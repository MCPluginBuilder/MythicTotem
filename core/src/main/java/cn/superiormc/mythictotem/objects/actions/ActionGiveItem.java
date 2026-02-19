package cn.superiormc.mythictotem.objects.actions;

import cn.superiormc.mythictotem.methods.BuildItem;
import cn.superiormc.mythictotem.objects.singlethings.AbstractThingData;
import cn.superiormc.mythictotem.utils.CommonUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionGiveItem extends AbstractRunAction {

    public ActionGiveItem() {
        super("give_item");
        setRequiredArgs("item");
    }

    @Override
    protected void onDoAction(ObjectSingleAction singleAction, Player player, AbstractThingData thingData) {
        ItemStack item = BuildItem.buildItemStack(player, singleAction.getSection().getConfigurationSection("item"), singleAction.getInt("amount"));
        CommonUtil.giveOrDrop(player, item);
    }
}
