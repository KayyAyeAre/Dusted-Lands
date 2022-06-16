package dusted.ai.types;

import arc.util.*;
import dusted.entities.abilities.*;
import dusted.entities.units.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class QuakeAI extends GroundAI {
    @Override
    public void updateTargeting() {
        super.updateTargeting();
        Structs.each(a -> {
            if (a instanceof QuakeAbility quakeAbility) quakeAbility.quake();
        }, unit.abilities);
    }
}
