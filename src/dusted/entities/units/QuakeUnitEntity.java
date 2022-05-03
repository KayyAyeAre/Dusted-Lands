package dusted.entities.units;

import arc.util.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class QuakeUnitEntity extends MechUnit {
    public static int classID;
    public int steps;
    public float quakeCooldown;
    public float quakeDelay;

    @Override
    public void update() {
        super.update();
        if (quakeCooldown > 0) quakeCooldown -= Time.delta;

        if (quakeDelay <= 0) {
            steps--;
            if (steps >= 0) {
                quakeDelay = qtype().quakeDelay;

                for (int line = 0; line < qtype().quakes; line++) {
                    Tmp.v1.trns(line * (360f / qtype().quakes) + rotation, (qtype().quakeSteps - (steps + 1)) * qtype().quakeSpacing).add(this);

                    Damage.damage(team, Tmp.v1.x, Tmp.v1.y, qtype().quakeRadius, qtype().quakeDamage);
                    qtype().quakeEffect.at(Tmp.v1);
                    qtype().quakeSound.at(Tmp.v1);
                }

                if (steps == 0) quakeCooldown = qtype().quakeCooldown;
            }
        }

        if (steps >= 0) {
            quakeDelay -= Time.delta;
        }
    }

    public void quake() {
        if (steps > 0 || quakeCooldown > 0 || quakeDelay > 0) return;
        steps = qtype().quakeSteps;
    }

    public QuakeUnitType qtype() {
        return (QuakeUnitType) type;
    }

    @Override
    public int classId() {
        return classID;
    }
}
