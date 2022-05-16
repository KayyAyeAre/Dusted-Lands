package dusted;

import arc.*;
import dusted.content.*;
import dusted.input.*;
import mindustry.*;
import mindustry.mod.*;

public class DustedLands extends Mod {
    public static DustedInput input;

    public DustedLands() {
        Vars.enableConsole = true;
        Core.app.addListener(input = new DustedInput());
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
