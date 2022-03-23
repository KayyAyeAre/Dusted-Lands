package dusted.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import dusted.world.meta.values.*;
import mindustry.ctype.*;
import mindustry.ui.*;

public class Powder extends UnlockableContent {
    public Color color;
    public float weight = 0.4f;

    public Powder(String name, Color color) {
        super(name);
        this.color = color;

        this.localizedName = Core.bundle.get("powder." + this.name + ".name", this.name);
        this.description = Core.bundle.getOrNull("powder." + this.name + ".description");
        this.details = Core.bundle.getOrNull("powder." + this.name + ".details");
    }

    public Powder(String name) {
        this(name, new Color(Color.black));
    }

    @Override
    public void setStats() {
        super.setStats();
        new CustomStatValue("weight", weight).add(stats);
    }

    @Override
    public TextureRegion icon(Cicon icon) {
        if (cicons[icon.ordinal()] == null) {
            cicons[icon.ordinal()] =
                Core.atlas.find("powder-" + name + "-" + icon.name(),
                Core.atlas.find("powder-" + name + "-full",
                Core.atlas.find(name + "-" + icon.name(),
                Core.atlas.find(name + "-full",
                Core.atlas.find(name,
                Core.atlas.find("powder-" + name,
                Core.atlas.find(name + "1")))))));
        }
        return cicons[icon.ordinal()];
    }

    @Override
    public ContentType getContentType() {
        return ContentType.effect_UNUSED;
    }
}
