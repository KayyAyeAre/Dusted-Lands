package dusted.content;

import arc.graphics.*;
import dusted.entities.bullet.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;

public class DustedBullets {
    public static BulletType titaniumSpray, quartzCascadeShot, cavnenCascadeShot;

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

        quartzCascadeShot = new CascadingBulletType(0, 40f, "white") {{
            frontColor = Color.valueOf("ffe8ee");
            backColor = Color.clear;
            hitSize = 12f;
            width = height = 12f;
            shrinkX = shrinkY = 0.3f;
            lifetime = 90f;
            cascadeTime = 21.6f;
            cascadeSpacing = 14f;
            cascadeAmount = 20;
            pierce = true;
            status = StatusEffects.burning;
            statusDuration = 120f;
        }};

        cavnenCascadeShot = new CascadingBulletType(0, 40f, "white") {{
            frontColor = Color.valueOf("6d726b");
            backColor = Color.clear;
            hitSize = 14f;
            width = height = 14f;
            shrinkX = shrinkY = 0.3f;
            lifetime = 90f;
            cascadeTime = 24f;
            cascadeSpacing = 14f;
            cascadeAmount = 20;
            pierce = true;
            status = DustedStatusEffects.deteriorating;
            statusDuration = 90f;
        }};
    }
}
