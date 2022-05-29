package dusted.ai.types;

import dusted.entities.abilities.*;
import dusted.entities.units.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class QuakeAI extends GroundAI {
    @Override
    public void faceTarget() {
        if (unit.type.omniMovement || unit instanceof Mechc) {
            QuakeAbility ability = (QuakeAbility) unit.abilities.find(a -> a instanceof QuakeAbility);
            if (!Units.invalidateTarget(target, unit, unit.range()) && unit.type.rotateShooting && unit.type.hasWeapons()) {
                unit.lookAt(Predict.intercept(unit, target, ability.quakeSpacing / ability.quakeDelay));
            } else if (unit.moving()) {
                unit.lookAt(unit.vel().angle());
            }
        }
    }

    @Override
    public void updateWeapons() {
        super.updateWeapons();
        if (target != null) unit.abilities.each(a -> a instanceof QuakeAbility, a -> ((QuakeAbility) a).quake());
    }
}
