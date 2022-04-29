package dusted.entities.units;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class BouncingUnitEntity extends UnitEntity {
    public Vec2 targetpos = new Vec2();
    public float bounceDelay;
    public float bounceTimer;
    public int steps;

    //spaghetti
    @Override
    public void update() {
        super.update();
        if (bounceTimer > 0) {
            bounceTimer -= Time.delta;
        }

        if (bounceDelay <= 0) {
            steps--;
            if (steps >= 0) {
                bounceDelay = btype().bounceDelay;

                rotation = angleTo(targetpos);
                Tmp.v1.trns(rotation, btype().bounceDistance);
                btype().bounceEffect.at(Mathf.lerp(x, x + Tmp.v1.x, 0.5f), Mathf.lerp(y, y + Tmp.v1.y, 0.5f), rotation, btype().bounceDistance);
                Vars.world.raycastEachWorld(x, y, x + Tmp.v1.x, y + Tmp.v1.y, (rx, ry) -> {
                    Units.nearbyEnemies(team, rx * Vars.tilesize, ry * Vars.tilesize, 8f, u -> u.damage(btype().bounceDamage));
                    return false;
                });
                x += Tmp.v1.x;
                y += Tmp.v1.y;
                btype().bounceSound.at(this, Mathf.random(btype().minBouncePitch, btype().maxBouncePitch));
                if (steps == 0) bounceTimer = btype().bounceCooldown;
            }
        }

        if (steps >= 0) {
            bounceDelay -= Time.delta;
        }
    }

    public void bounce() {
        if (bounceDelay > 0 || bounceTimer > 0 || steps > 0) return;
        targetpos.setZero();
        Unit target = Units.closestEnemy(team, x, y, btype().bounceDistance, u -> true);
        if (target != null) {
            targetpos.set(target);
        } else {
            targetpos.trns(rotation, btype().bounceDistance / 2).add(this);
        }

        steps = btype().bounces;
    }

    //lazy
    public BouncingUnitType btype() {
        return (BouncingUnitType) type;
    }
}
