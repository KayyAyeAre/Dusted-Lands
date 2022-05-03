package dusted.ai.types;

import dusted.entities.units.*;
import mindustry.ai.types.*;

public class BounceAI extends FlyingAI {
    @Override
    public void updateWeapons() {
        super.updateWeapons();
        if (target != null && unit instanceof BouncingUnitEntity bunit) bunit.bounce(target);
    }
}
