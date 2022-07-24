package dusted.entities.abilities;

import arc.*;
import arc.audio.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

//TODO this could just be a weapon
public class QuakeAbility extends Ability {
    public float quakeDamage = 60f;
    public float quakeSpacing = 20f;
    public int quakeSteps = 1;
    public int quakes = 1;
    public float quakeRadius = 8f;
    public float quakeDelay = 10f;
    public float quakeCooldown = 40f;
    public Effect quakeEffect = Fx.explosion;
    public Sound quakeSound = Sounds.explosion;

    protected int steps;
    protected float entityQuakeCooldown;
    protected float entityQuakeDelay;

    @Override
    public void update(Unit unit) {
        if (entityQuakeCooldown > 0) entityQuakeCooldown -= Time.delta;

        if (entityQuakeDelay <= 0) {
            steps--;
            if (steps >= 0) {
                entityQuakeDelay = quakeDelay;

                for (int line = 0; line < quakes; line++) {
                    Tmp.v1.trns(line * (360f / quakes) + unit.rotation, (quakeSteps - (steps + 1)) * quakeSpacing).add(unit);

                    Damage.damage(unit.team, Tmp.v1.x, Tmp.v1.y, quakeRadius, quakeDamage);
                    quakeEffect.at(Tmp.v1);
                    quakeSound.at(Tmp.v1);
                }

                if (steps == 0) entityQuakeCooldown = quakeCooldown;
            }
        }

        if (steps >= 0) {
            entityQuakeDelay -= Time.delta;
        }
    }

    public void quake() {
        if (steps > 0 || entityQuakeCooldown > 0 || entityQuakeDelay > 0) return;
        steps = quakeSteps;
    }

    @Override
    public String localized() {
        return Core.bundle.get("ability.quake");
    }
}
