package dusted.type;

import arc.*;
import arc.graphics.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class Powder extends UnlockableContent {
    public Color color;
    public StatusEffect effect = StatusEffects.none;

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
