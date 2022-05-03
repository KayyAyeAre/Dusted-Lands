package dusted.ai.types;

import dusted.entities.units.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.gen.*;

public class QuakeAI extends GroundAI {
    @Override
    public void faceTarget() {
        if (unit.type.omniMovement || unit instanceof Mechc) {
            QuakeUnitEntity qunit = (QuakeUnitEntity) unit;
            if (!Units.invalidateTarget(target, unit, unit.range()) && unit.type.rotateShooting && unit.type.hasWeapons()) {
                unit.lookAt(Predict.intercept(unit, target, qunit.qtype().quakeSpacing / qunit.qtype().quakeDelay));
            } else if (unit.moving()) {
                unit.lookAt(unit.vel().angle());
            }
        }
    }

    @Override
    public void updateWeapons() {
        super.updateWeapons();
        if (target != null && unit instanceof QuakeUnitEntity qunit) qunit.quake();
    }
}
