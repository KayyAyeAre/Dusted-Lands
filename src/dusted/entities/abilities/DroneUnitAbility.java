package dusted.entities.abilities;

import arc.*;
import arc.math.*;
import arc.util.*;
import dusted.ai.types.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.type.*;

//copy of unitspawnability which only spawns one unit and can control the spawned unit
//TODO problems with id saving
public class DroneUnitAbility extends Ability {
    public UnitType droneType;
    public float spawnTime, spawnX, spawnY;
    public Effect spawnEffect = Fx.spawn;

    public Unit droneUnit;
    protected float constructTime;

    public DroneUnitAbility(UnitType droneType, float spawnTime, float spawnX, float spawnY) {
        this.droneType = droneType;
        this.spawnTime = spawnTime;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    @Override
    public void update(Unit unit) {
        Core.app.post(() -> {
            if (droneUnit != null && (!unit.isAdded() || unit.team != droneUnit.team)) {
                Call.unitDespawn(droneUnit);
            }
        });

        if (droneUnit == null) {
            Log.info(data);
            if (data != 0f) {
                droneUnit = Groups.unit.getByID((int) data);
                return;
            }

            constructTime += Time.delta * Vars.state.rules.unitBuildSpeed(unit.team);

            if (constructTime >= spawnTime && Units.canCreate(unit.team, droneType)) {
                float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
                spawnEffect.at(x, y, 0f);
                Unit u = droneType.create(unit.team);
                u.set(x, y);
                u.rotation = unit.rotation;

                Events.fire(new UnitCreateEvent(u, null, unit));
                if (!Vars.net.client()) {
                    u.add();
                }

                droneUnit = u;

                constructTime = 0f;
            }
        } else {
            if (droneUnit.dead || !droneUnit.isAdded()) {
                droneUnit = null;
                return;
            }

            if (droneUnit.controller() instanceof DroneAI ai && ai.owner != unit) {
                ai.owner = unit;
            }

            //kinda stupid but it works
            data = droneUnit.id + 0.5f;
        }
    }
}
