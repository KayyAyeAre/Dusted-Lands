package dusted.entities.units;

import arc.audio.*;
import dusted.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;

public class BouncingUnitType extends UnitType {
    public Effect bounceEffect = DustedFx.smallBounce;
    public float bounceDistance = 120f;
    public float bounceCooldown = 30f;
    public float bounceDelay = 15f;
    public float bounceDamage = 60f;
    public int bounces = 1;
    public Sound bounceSound = Sounds.shotgun;
    public float minBouncePitch = 0.9f, maxBouncePitch = 1.1f;

    public BouncingUnitType(String name) {
        super(name);
    }
}
