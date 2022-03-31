package dusted.content;

import dusted.entities.bullet.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;

public class DustedBullets {
    public static BulletType titaniumSpray, eruptionFireball;

    public static void load() {
        titaniumSpray = new BulletType(3.25f, 0) {{
            hitSize = 8f;
            lifetime = 18f;
            pierce = true;
            shootEffect = DustedFx.shootTitaniumSpray;
            hitEffect = DustedFx.hitTitaniumSpray;
            despawnEffect = Fx.none;
            status = DustedStatusEffects.sprayed;
            statusDuration = 60f * 3;
            hittable = false;
        }};

        eruptionFireball = new EruptionBulletType(1.2f, 160) {{
            splashDamage = 165;
            splashDamageRadius = 60;
            collidesTiles = false;
            collides = false;
            drag = -0.1f;
            hitEffect = Fx.explosion;
        }};
    }
}
