package dusted.content;

import mindustry.content.*;
import mindustry.entities.bullet.*;

public class DustedBullets {
    public static BulletType titaniumSpray;

    public static void load() {
        titaniumSpray = new BulletType(3.25f, 0) {{
            hitSize = 8f;
            lifetime = 18f;
            pierce = true;
            shootEffect = DustedFx.shootTitaniumSpray;
            hitEffect = DustedFx.hitTitaniumSpray;
            despawnEffect = Fx.none;
            status = DustedStatusEffects.sprayed;
            statusDuration = 60f * 12;
            hittable = false;
        }};
    }
}
