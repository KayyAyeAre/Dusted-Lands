package dusted.entities.abilities;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.content.*;
import dusted.graphics.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class RevolvingOrbAbility extends Ability {
    public float radius;
    public float orbRadius;
    public float orbCooldown;
    public float rotationSpeed;
    public float damage;
    public int orbs = 1;
    public float orbLifetime;
    public Color orbColor = DustedPal.decayingYellow;
    public Sound summonSound = Sounds.lasershoot;
    public Effect summonEffect = DustedFx.orbSummon;

    protected boolean runEffect;
    protected float lifetime;
    protected float rotation;
    protected float cooldown;

    @Override
    public void update(Unit unit) {
        super.update(unit);

        if (runEffect) {
            summonSound.at(unit.x, unit.y);
            for (int i = 0; i < orbs; i++) summonEffect.at(unit.x + Angles.trnsx(rotation + ((360f / orbs) * i), radius), unit.y + Angles.trnsy(rotation + ((360f / orbs) * i), radius));
            runEffect = false;
        }
        rotation = (lifetime * rotationSpeed);
        if (lifetime > 0) {
            for (int i = 0; i < orbs; i++) {
                Groups.bullet.intersect(unit.x + Angles.trnsx(rotation + ((360f / orbs) * i), radius), unit.y + Angles.trnsy(rotation + ((360f / orbs) * i), radius), orbRadius * 2.2f, orbRadius * 2.2f, b -> {
                    if (b.team == unit.team || !b.type.hittable) return;
                    if (b.damage > damage) {
                        b.damage(b.damage() - damage);
                    } else {
                        b.remove();
                    }
                });
                Damage.damage(unit.team, unit.x + Angles.trnsx(rotation + ((360f / orbs) * i), radius), unit.y + Angles.trnsy(rotation + ((360f / orbs) * i), radius), orbRadius, damage);
            }
            lifetime -= Time.delta;
        }
        if (cooldown > 0) cooldown -= Time.delta;
    }

    public void summon() {
        if (lifetime > 0 || cooldown > 0) return;
        lifetime = orbLifetime;
        rotation = 0;
        cooldown = orbLifetime + orbCooldown;
        runEffect = true;
    }

    @Override
    public void draw(Unit unit) {
        if (lifetime <= 0) return;
        Draw.z(Layer.bullet);
        Draw.color(orbColor);
        for (int i = 0; i < orbs; i++) {
            Fill.circle(unit.x + Angles.trnsx(rotation + ((360f / orbs) * i), radius), unit.y + Angles.trnsy(rotation + ((360f / orbs) * i), radius), orbRadius * (lifetime / orbLifetime));
        }
        Draw.reset();
    }

    @Override
    public String localized() {
        return Core.bundle.get("ability.revolving-orb");
    }
}
