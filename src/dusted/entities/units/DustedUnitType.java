package dusted.entities.units;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import dusted.ai.types.*;
import dusted.content.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.type.*;

public class DustedUnitType extends UnitType {
    public float quakeDamage = 60f;
    public float quakeSpacing = 20f;
    public int quakeSteps = 1;
    public int quakes = 1;
    public float quakeRadius = 8f;
    public float quakeDelay = 10f;
    public float quakeCooldown = 40f;
    public Effect quakeEffect = Fx.explosion;
    public Sound quakeSound = Sounds.explosion;
    public DustedUnitCategory unitCategory;

    public Effect bounceEffect = DustedFx.smallBounce;
    public float bounceDistance = 120f;
    public float bounceCooldown = 30f;
    public float bounceDelay = 15f;
    public float bounceDamage = 10f;
    public int bounces = 1;
    public Sound bounceSound = Sounds.shotgun;
    public float minBouncePitch = 0.9f, maxBouncePitch = 1.1f;

    public DustedUnitType(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        switch (unitCategory) {
            case quake:
                defaultController = QuakeAI::new;
                break;
            case bounce:
                defaultController = BounceAI::new;
                break;
        }
    }

    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);
        PixmapRegion base = new PixmapRegion(packer.get(name + "-outline").crop());

        //TODO broken
        //Pixmap cell = Core.atlas.getPixmap(name + "-cell").pixmap.copy();
        //cell.replace(in -> in == 0xffffffff ? 0xffa664ff : in == 0xdcc6c6ff || in == 0xdcc5c5ff ? 0xd06b53ff : 0);
        //base.pixmap.draw(cell, base.width / 2 - (cell.width / 2), base.height / 2 + (cell.height / 2), true);

        for (Weapon weapon : weapons) {
            if (!weapon.name.isEmpty()) {
                Pixmap weaponRegion = packer.get(weapon.name + "-outline").crop();
                int wx = (base.width / 2) + (((int) weapon.x * 4) - (weaponRegion.width / 2));
                int wy = (base.height / 2) + (((int) weapon.y * 4) + (weaponRegion.height / 2));
                base.pixmap.draw(weaponRegion, wx, wy, true);

                if (weapon.mirror) {
                    int wfx = (base.width / 2) - (((int) weapon.x * 4) + (weaponRegion.width / 2));
                    Pixmap flipRegion = weaponRegion.flipX();
                    base.pixmap.draw(flipRegion, wfx, wy, true);
                }
            }
        }

        packer.add(PageType.main, name + "-full", base);
    }

    public enum DustedUnitCategory {
        quake,
        bounce
    }
}
