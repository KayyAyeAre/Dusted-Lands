package dusted.world.blocks.environment;

import dusted.type.*;
import dusted.world.interfaces.*;
import mindustry.world.blocks.environment.*;

public class PowderFloor extends Floor {
    public Powder powderDrop;
    public float powderMultiplier = 1f;

    public PowderFloor(String name) {
        super(name);
    }
}
