package cn.superiormc.mythictotem.objects.singlethings;

import cn.superiormc.mythictotem.objects.checks.ObjectCheck;
import cn.superiormc.mythictotem.objects.checks.ObjectPlaceCheck;
import org.bukkit.Location;

public class TotemActiveData extends AbstractThingData {

    public Location startLocation;

    public ObjectCheck check;

    public ObjectPlaceCheck totem;

    public TotemActiveData(Location startLocation, ObjectCheck check, ObjectPlaceCheck totem) {
        this.startLocation = startLocation;
        this.check = check;
        this.totem = totem;
    }

}
