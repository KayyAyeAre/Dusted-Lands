package dusted.entities.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;

public class FinUnitType extends DustedUnitType {
    public Seq<FinPart> fins = new Seq<>();
    public float finScl = 25f;
    public float groundSpeedMultiplier = 0.4f;

    public FinUnitType(String name) {
        super(name);
        omniMovement = false;
    }

    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);

        for (int i = 0; i < fins.size; i++) {
            Pixmap outline = Pixmaps.outline(Core.atlas.getPixmap(name + "-fin" + (i + 1)), outlineColor, outlineRadius);
            Drawf.checkBleed(outline);
            packer.add(PageType.main, name + "-fin" + (i + 1), outline);
        }
    }

    @Override
    public void load() {
        super.load();

        for (int i = 0; i < fins.size; i++) {
            fins.get(i).region = Core.atlas.find(name + "-fin" + (i + 1));
        }
    }

    @Override
    public void drawBody(Unit unit) {
        applyColor(unit);

        if (unit instanceof FinUnitEntity finUnit) {
            fins.each(fin -> {
                for (int i : Mathf.signs) {
                    Draw.xscl = i;
                    Draw.rect(
                            fin.region,
                            unit.x + Angles.trnsx(unit.rotation - 90, fin.x * i, fin.y),
                            unit.y + Angles.trnsy(unit.rotation - 90, fin.x * i, fin.y),
                            (unit.rotation - 90) + (fin.rotation * i) + (Mathf.sin(finUnit.finTime, finScl, fin.mag) * i)
                            );
                }
                Draw.xscl = 1f;
            });
        }

        super.drawBody(unit);
    }

    public static class FinPart {
        public float x, y, rotation, mag;
        public TextureRegion region;

        public FinPart(float x, float y, float rotation, float mag) {
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.mag = mag;
        }
    }
}
