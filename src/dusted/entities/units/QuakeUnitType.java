package dusted.entities.units;

import arc.audio.*;
import dusted.ai.types.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;

public class QuakeUnitType extends UnitType {
    public float quakeDamage = 60f;
    public float quakeSpacing = 20f;
    public int quakeSteps = 1;
    public int quakes = 1;
    public float quakeRadius = 8f;

    public float quakeDelay = 10f;
    public float quakeCooldown = 40f;

    public Effect quakeEffect = Fx.explosion;
    public Sound quakeSound = Sounds.explosion;

    public QuakeUnitType(String name) {
        super(name);
        defaultController = QuakeAI::new;
    }

    @Override
    public boolean hasWeapons() {
        return true;
    }
}
