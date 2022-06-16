package dusted.ai.types;

import arc.util.*;
import dusted.entities.abilities.*;
import mindustry.ai.types.*;

public class BounceAI extends FlyingAI {
    @Override
    public void updateTargeting() {
        super.updateTargeting();
        Structs.each(a -> {
            if (a instanceof BounceAbility bounceAbility) bounceAbility.bounce(unit, target);
        }, unit.abilities);
    }
}
