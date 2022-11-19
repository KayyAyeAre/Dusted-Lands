package dusted.type;

import arc.*;
import arc.graphics.*;
import dusted.world.meta.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.meta.*;

//TODO need something that'll make this not be liquid 2.0
public class Powder extends UnlockableContent {
    public Color color, clumpColor;
    //TODO none of these stats are even used...
    public float temperature = 0.5f;
    public float flammability;
    public float explosiveness;
    public StatusEffect effect = StatusEffects.none;

    public Powder(String name, Color color, Color clumpColor) {
        super(name);
        this.color = color;
        this.clumpColor = clumpColor;

        this.localizedName = Core.bundle.get("powder." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull("powder." + this.name + ".description");
        this.details = Core.bundle.getOrNull("powder." + this.name + ".details");
    }

    public Powder(String name) {
        this(name, new Color(Color.black), new Color(Color.black));
    }

    @Override
    public void setStats() {
        stats.addPercent(Stat.temperature, temperature);
        stats.addPercent(Stat.flammability, flammability);
        stats.addPercent(Stat.explosiveness, explosiveness);
    }

    @Override
    public void loadIcon() {
        fullIcon = Core.atlas.find("powder-" + name + "full",
                Core.atlas.find(name + "-full",
                        Core.atlas.find(name,
                                Core.atlas.find(name + "-full",
                                        Core.atlas.find(name,
                                                Core.atlas.find("powder-" + name,
                                                        Core.atlas.find(name + "1")))))));

        uiIcon = Core.atlas.find("powder-" + name + "-ui", fullIcon);
    }

    @Override
    public ContentType getContentType() {
        return ContentType.effect_UNUSED;
    }
}
