package dusted.entities.abilities;

import arc.graphics.g2d.*;
import arc.util.*;
import dusted.*;
import dusted.content.*;
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
        type.clipSize = Math.max(type.clipSize, radius * 2f);
    }

    @Override
    public void update(Unit unit) {
        if (!added) {
            DustedLands.decay.shields.add(shield = new DecayShield(unit, radius));
            added = true;
        }
    }

    @Override
    public void death(Unit unit) {
        super.death(unit);
        DustedFx.shieldFade.at(unit.x, unit.y, 0, shield);
        DustedLands.decay.shields.remove(shield);
    }

    @Override
    public void draw(Unit unit) {
        if (shield != null) shield.draw();
    }
}
