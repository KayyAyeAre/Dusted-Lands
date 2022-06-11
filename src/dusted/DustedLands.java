package dusted;

import arc.*;
import arc.struct.*;
import dusted.content.*;
import dusted.input.*;
import dusted.world.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.world.*;

public class DustedLands extends Mod {
    public static DustedInput input;

    public DustedLands() {
        Vars.enableConsole = true;
        Core.app.addListener(input = new DustedInput());
        VolcanoSpawner.load();
    }

    @Override
    public void loadContent() {
        DustedStatusEffects.load();
        DustedItems.load();
        DustedPowders.load();
        DustedUnitTypes.load();
        DustedBlocks.load();
        DustedWeathers.load();
        //DustedPlanets.load();
        //DustedSectorPresets.load();
        DustedTechTree.load();
    }
}
