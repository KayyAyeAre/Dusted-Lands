package dusted.entities.bullet;

import arc.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class InstantBulletType extends BulletType {
    public float distance = 240f;

    public InstantBulletType() {
        speed = 0f;
    }

    @Override
    protected float calculateRange() {
        return Math.max(distance, maxRange);
    }

    @Override
    public void init(Bullet b) {
        Seq<Unit> units = new Seq<>();
        Rect rect = new Rect(), hitrect = new Rect();
        Vec2 vec = new Vec2();

        Unit unit = null;
        Building build;

        vec.trnsExact(b.rotation(), distance);

        rect.setPosition(b.x, b.y).setSize(vec.x, vec.y).normalize().grow(6f);

        Units.nearbyEnemies(b.team, rect, u -> {
            if (u.checkTarget(collidesAir, collidesGround)) units.add(u);
        });

        units.sort(u -> u.dst2(b));
        for (Unit u : units) {
            if (!u.hittable()) return;

            u.hitbox(hitrect);

            Vec2 hvec = Geometry.raycastRect(b.x, b.y, b.x + vec.x, b.y + vec.y, hitrect.grow(6f));

            if (hvec != null) {
                unit = u;
                Tmp.v2.set(hvec);
                break;
            }
        }

        Building[] buildf = {null};
        World.raycastEachWorld(b.x, b.y, b.x + vec.x, b.y + vec.y, (x, y) -> {
            Building hit = Vars.world.build(x, y);

            if (hit != null && b.checkUnderBuild(hit, hit.x, hit.y) && hit.team != b.team && hit.collide(b)) {
                buildf[0] = hit;
                return true;
            }

            return false;
        });
        build = buildf[0];

        if (build != null && unit != null) {
            float bdst = build.dst2(b), udst = unit.dst2(b);

            if (bdst < udst) {
                build.collision(b);
                hit(b, build.x, build.y);
            } else {
                unit.collision(b, Tmp.v2.x, Tmp.v2.y);
                b.collision(unit, Tmp.v2.x, Tmp.v2.y);
            }
        } else if (build != null) {
            build.collision(b);
            hit(b, build.x, build.y);
        } else if (unit != null) {
            unit.collision(b, Tmp.v2.x, Tmp.v2.y);
            b.collision(unit, Tmp.v2.x, Tmp.v2.y);
        }

        if (!b.hit) {
            Core.app.post(() -> {
                b.set(b.x + vec.x, b.y + vec.y);
                b.remove();
            });
        }
    }
}
