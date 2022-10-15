package dusted.graphics;

import arc.audio.*;
import mindustry.*;

public class DustedSounds {
    public static Sound synthesisLoop, decayLoop, shootLight, cannonLight, cannonHeavy, fireworkLaunch, crackle;

    static {
        synthesisLoop = Vars.tree.loadSound("synthesisLoop");
        decayLoop = Vars.tree.loadSound("decayLoop");
        shootLight = Vars.tree.loadSound("shootLight");
        cannonLight = Vars.tree.loadSound("cannonLight");
        cannonHeavy = Vars.tree.loadSound("cannonHeavy");
        fireworkLaunch = Vars.tree.loadSound("fireworkLaunch");
        crackle = Vars.tree.loadSound("crackle");
    }
}
