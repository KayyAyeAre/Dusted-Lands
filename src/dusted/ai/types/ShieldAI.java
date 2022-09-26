package dusted.ai.types;

import arc.struct.*;
import arc.util.*;
import dusted.entities.abilities.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

import static dusted.DustedLands.*;

public class ShieldAI extends FlyingAI {
    public Seq<Unit> nearby = new Seq<>();
    public static ObjectMap<Unit, Unit> willShield = new ObjectMap<>();

    @Override
    public void updateMovement() {
        unloadPayloads();

        nearby.each(u -> {
            if (u.dead()) {
                nearby.remove(u);
                willShield.remove(u);
            }
        });

        if (nearby.any()) {
            float x = 0f, y = 0f;

            for (Unit unit : nearby) {
                x += unit.x;
                y += unit.y;
            }

            x /= nearby.size;
            y /= nearby.size;

            moveTo(Tmp.v1.set(x, y), 15f, 10f);
        } else {
            Log.info("searching");
            float margin = 20f;

            Unit following = Units.closest(unit.team, unit.x, unit.y, u -> u != unit && !u.dead() && (!decay.isShielded(u)));

            if (following != null && !willShield.containsKey(following)) {
                Units.nearby(following.team(), following.x(), following.y(), followRadius() - margin, nearby::add);
                nearby.each(u -> willShield.put(u, unit));
                Log.info("found units @", nearby);
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
