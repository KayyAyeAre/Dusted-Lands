package dusted.ai;

import dusted.ai.types.*;
import mindustry.ai.*;

public class DustedUnitCommands {
    public static final UnitCommand shieldCommand = new UnitCommand("shield", "defense", u -> new ShieldAI());
}
