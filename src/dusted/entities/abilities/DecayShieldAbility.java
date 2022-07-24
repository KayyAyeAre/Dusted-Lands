package dusted.entities.abilities;

import arc.graphics.g2d.*;
import arc.util.*;
import dusted.*;
import dusted.game.Decay.*;
import mindustry.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;

public class DecayShieldAbility extends Ability {
    public float radius;

    protected boolean added = false;
    protected DecayShield shield;

    public DecayShieldAbility(float radius) {
        this.radius = radius;
    }

    @Override
    public void init(UnitType type) {
        super.init(type);
        type.clipSize = Math.max(type.clipSize, radius);
    }

    @Override
    public void update(Unit unit) {
        if (!added) {
            DustedLands.decay.shields.add(shield = new DecayShield(() -> Tmp.v1.set(unit), () -> unit.team, () -> radius));
            added = true;
        }
    }

    @Override
    public void death(Unit unit) {
        super.death(unit);
        DustedLands.decay.shields.remove(shield);
    }

    @Override
    public void draw(Unit unit) {
        Draw.z(Layer.shields + 2.5f);
        Draw.color(unit.team.color);

        if (Vars.renderer.animateShields) {
            Fill.poly(unit.x, unit.y, 6, radius);
        } else {
            Lines.stroke(1.5f);
            Draw.alpha(0.09f);
            Fill.poly(unit.x, unit.y, 6, radius);
            Draw.alpha(1f);
            Lines.poly(unit.x, unit.y, 6, radius);
        }
    }
}
