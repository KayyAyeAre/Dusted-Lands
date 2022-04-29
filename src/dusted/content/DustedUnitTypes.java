package dusted.content;

import dusted.entities.units.*;
import dusted.entities.units.definitions.*;
import mindustry.gen.*;
import mindustry.type.*;

public class DustedUnitTypes {
    public static UnitType
            carom, recur, saltate, ricochet, recrudesce,
            cavanimate
            ;

    public static void load() {
        UnitEntityCarom.classID = EntityMapping.register("dusted-lands-carom", UnitEntityCarom::new);
        carom = new BouncingUnitType("carom") {{
            speed = 3f;
            flying = true;
            bounces = 2;
            health = 80;
            commandLimit = 4;
            circleTarget = true;
        }};

        //TODO
        //cavanimate = new UnitType("cavanimate");
    }
}
