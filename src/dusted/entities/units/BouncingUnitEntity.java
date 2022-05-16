package dusted.entities.units;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class BouncingUnitEntity extends UnitEntity {
    public static int classID;
    public Vec2 targetpos = new Vec2();
    public float bounceDelay;
    public float bounceCooldown;
    public int steps;

    //spaghetti
    @Override
    public void update() {
        super.update();
        if (bounceCooldown > 0) bounceCooldown -= Time.delta;

        if (steps >= 0) {
            bounceDelay -= Time.delta;
        }

        if (bounceDelay <= 0) {
            steps--;
            if (steps >= 0) {
                bounceDelay = dtype().bounceDelay;

                rotation = angleTo(targetpos);
                Tmp.v1.trns(rotation, dtype().bounceDistance);
                dtype().bounceEffect.at(Mathf.lerp(x, x + Tmp.v1.x, 0.5f), Mathf.lerp(y, y + Tmp.v1.y, 0.5f), rotation, dtype().bounceDistance);
                Vars.world.raycastEachWorld(x, y, x + Tmp.v1.x, y + Tmp.v1.y, (rx, ry) -> {
                    Damage.damage(team, rx * 8, ry * 8, 8f, dtype().bounceDamage);
                    return false;
                });
                x += Tmp.v1.x;
                y += Tmp.v1.y;
                dtype().bounceSound.at(this, Mathf.random(dtype().minBouncePitch, dtype().maxBouncePitch));
                if (steps == 0) bounceCooldown = dtype().bounceCooldown;
            }
        }
    }

    public void bounce(Position target) {
        if (bounceDelay > 0 || bounceCooldown > 0 || steps > 0) return;
        targetpos.set(target);
        steps = dtype().bounces;
    }

    public void bounce() {
        if (bounceDelay > 0 || bounceCooldown > 0 || steps > 0) return;
        targetpos.setZero();
        Unit target = Units.closestEnemy(team, x, y, dtype().bounceDistance, u -> true);
        if (target != null) {
            targetpos.set(target);
        } else {
            targetpos.trns(rotation, dtype().bounceDistance / 2).add(this);
        }

        steps = dtype().bounces;
    }

    //lazy
    public DustedUnitType dtype() {
        return (DustedUnitType) type;
    }

    @Override
    public int classId() {
        return classID;
    }
}
