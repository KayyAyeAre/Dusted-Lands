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
                quakeDelay = dtype().quakeDelay;

                for (int line = 0; line < dtype().quakes; line++) {
                    Tmp.v1.trns(line * (360f / dtype().quakes) + rotation, (dtype().quakeSteps - (steps + 1)) * dtype().quakeSpacing).add(this);

                    Damage.damage(team, Tmp.v1.x, Tmp.v1.y, dtype().quakeRadius, dtype().quakeDamage);
                    dtype().quakeEffect.at(Tmp.v1);
                    dtype().quakeSound.at(Tmp.v1);
                }

                if (steps == 0) quakeCooldown = dtype().quakeCooldown;
            }
        }

        if (steps >= 0) {
            quakeDelay -= Time.delta;
        }
    }

    public void quake() {
        if (steps > 0 || quakeCooldown > 0 || quakeDelay > 0) return;
        steps = dtype().quakeSteps;
    }

    public DustedUnitType dtype() {
        return (DustedUnitType) type;
    }

    @Override
    public int classId() {
        return classID;
    }
}
