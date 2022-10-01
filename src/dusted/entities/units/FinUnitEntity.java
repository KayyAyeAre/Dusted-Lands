package dusted.entities.units;

import arc.math.*;
import mindustry.gen.*;

public class FinUnitEntity extends ElevationMoveUnit {
    public float finTime = Mathf.random(100f);

    public static int classID;

    @Override
    public void update() {
        super.update();

        finTime += vel.len();
    }

    @Override
    public float floorSpeedMultiplier() {
        return !(type instanceof FinUnitType funit) || (floorOn().isDeep() || floorOn().isLiquid) ? speedMultiplier : funit.groundSpeedMultiplier * speedMultiplier;
    }

    @Override
    public int classId() {
        return classID;
    }
}
