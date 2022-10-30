package dusted.ai.types;

import arc.math.geom.*;
import arc.struct.*;
import dusted.entities.abilities.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

import static dusted.DustedLands.*;

public class ShieldAI extends FlyingAI {
    public Seq<Unit> nearby = new Seq<>();
    public static ObjectMap<Unit, Unit> willShield = new ObjectMap<>();
    public Vec2 targetPos = new Vec2();

    public boolean checkPos;

    @Override
    public void updateMovement() {
        unloadPayloads();

        nearby.each(u -> {
            if (u.dead()) {
                nearby.remove(u);
                willShield.remove(u);
            }

            if (checkPos) {
                //unit isnt shielded
                if (!u.within(targetPos, followRadius())) {
                    nearby.remove(u);
                    willShield.remove(u);
                }

                //already targeted by another shielder
                if (willShield.get(u) != unit) {
                    nearby.remove(u);
                }
            }
        });

        if (nearby.any()) {
            checkPos = true;
            float x = 0f, y = 0f;

            for (Unit unit : nearby) {
                x += unit.x;
                y += unit.y;
            }

            x /= nearby.size;
            y /= nearby.size;

            moveTo(targetPos.set(x, y), 15f, 10f);
        } else {
            checkPos = false;
            targetPos.set(unit);
            float margin = 20f;

            Unit following = Units.closest(unit.team, unit.x, unit.y, u -> u != unit && !u.dead() && !decay.ignoreShield.contains(u.type) && (!decay.isShielded(u)));

            if (following != null && !willShield.containsKey(following)) {
                Units.nearby(following.team(), following.x(), following.y(), followRadius() - margin, u -> {
                    if (!decay.isShielded(u) && !decay.ignoreShield.contains(u.type)) nearby.add(u);
                });
                nearby.each(u -> willShield.put(u, unit));
            }
        }
    }

    public float followRadius() {
        float radius = 0f;

        for (Ability ability : unit.abilities) {
            if (ability instanceof DecayShieldAbility shield) radius = Math.max(radius, shield.radius);
        }

        return radius;
    }
}
