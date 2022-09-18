package dusted.ai.types;

import arc.struct.*;
import arc.util.*;
import dusted.entities.abilities.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class ShieldAI extends FlyingAI {
    public Seq<Unit> nearby = new Seq<>();

    @Override
    public void updateMovement() {
        unloadPayloads();

        if (nearby.any()) {
            float x = 0f, y = 0f;

            for (Unit unit : nearby) {
                x += unit.x;
                y += unit.y;
            }

            x /= nearby.size;
            y /= nearby.size;

            moveTo(Tmp.v1.set(x, y), 1f, 10f);
        }

        if (timer.get(timerTarget3, 30f)) {
            float margin = 10f;

            nearby.clear();
            Unit following = Units.closest(unit.team, unit.x, unit.y, u -> !u.dead() && u.type != unit.type);
            if (following != null) Units.nearby(following.team(), following.x(), following.y(), followRadius() - margin, nearby::add);
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
