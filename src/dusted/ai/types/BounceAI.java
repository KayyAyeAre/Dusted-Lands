package dusted.ai.types;

import dusted.entities.abilities.*;
import dusted.entities.units.*;
import mindustry.ai.types.*;

public class BounceAI extends FlyingAI {
    @Override
    public void updateWeapons() {
        super.updateWeapons();
        if (target != null) unit.abilities.each(a -> a instanceof BounceAbility, a -> ((BounceAbility) a).bounce(unit, target));
    }
}
