package dusted.entities.abilities;

import arc.*;
import arc.audio.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import dusted.content.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class BounceAbility extends Ability {
    public Effect bounceEffect = DustedFx.smallBounce;
    public float bounceDistance = 120f;
    public float bounceCooldown = 30f;
    public float bounceDelay = 15f;
    public float bounceDamage = 10f;
    public int bounces = 1;
    public Sound bounceSound = Sounds.shotgun;

    protected Vec2 targetpos = new Vec2();
    protected float entityBounceDelay;
    protected float entityBounceCooldown;
    protected int steps;

    @Override
    public void update(Unit unit) {
        if (entityBounceCooldown > 0) entityBounceCooldown -= Time.delta;

        if (steps >= 0) {
            entityBounceDelay -= Time.delta;
        }

        if (entityBounceDelay <= 0) {
            steps--;
            if (steps >= 0) {
                entityBounceDelay = bounceDelay;

                unit.rotation = unit.angleTo(targetpos);
                Tmp.v1.trns(unit.rotation, bounceDistance * unit.speedMultiplier());
                bounceEffect.at(Mathf.lerp(unit.x, unit.x + Tmp.v1.x, 0.5f), Mathf.lerp(unit.y, unit.y + Tmp.v1.y, 0.5f), unit.rotation, bounceDistance * unit.speedMultiplier());
                Vars.world.raycastEachWorld(unit.x, unit.y, unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, (rx, ry) -> {
                    Damage.damage(unit.team, rx * 8, ry * 8, 8f, bounceDamage);
                    return false;
                });
                unit.x += Tmp.v1.x;
                unit.y += Tmp.v1.y;
                bounceSound.at(unit, Mathf.random(0.9f, 1.1f));
                if (steps == 0) entityBounceCooldown = bounceCooldown;
            }
        }
    }

    public void bounce(Unit unit, Position target) {
        if (entityBounceDelay > 0 || entityBounceCooldown > 0 || steps > 0) return;
        targetpos.set(target);
        steps = bounces;
    }

    public void bounce(Unit unit) {
        if (entityBounceDelay > 0 || entityBounceCooldown > 0 || steps > 0) return;
        targetpos.setZero();
        Unit target = Units.closestEnemy(unit.team, unit.x, unit.y, bounceDistance * unit.speedMultiplier(), u -> true);
        if (target != null) {
            targetpos.set(target);
        } else {
            targetpos.trns(unit.rotation, (bounceDistance * unit.speedMultiplier()) / 2).add(unit);
        }

        steps = bounces;
    }

    @Override
    public String localized() {
        return Core.bundle.get("ability.bounce");
    }
}
