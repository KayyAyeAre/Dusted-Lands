package dusted.entities.abilities;

import arc.math.*;
import arc.util.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class AttractorAbility extends Ability {
    public float strength, range;

    public AttractorAbility(float strength, float range) {
        this.strength = strength;
        this.range = range;
    }

    @Override
    public void update(Unit unit) {
        Groups.bullet.intersect(unit.x - (range / 2f), unit.y - (range / 2f), range * 2f, range * 2f)
                .filter(b -> b.dst(unit) <= range).each(b -> {
                    b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(unit), strength * Time.delta * 50f));
                });
    }
}
