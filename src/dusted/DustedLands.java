package dusted;

import arc.*;
import dusted.content.*;
import dusted.input.*;
import dusted.world.*;
import mindustry.*;
import mindustry.mod.*;

public class DustedLands extends Mod {
    public DustedInputHandler inputHandler;

    public DustedLands() {
        Vars.enableConsole = true;
        Core.app.addListener(inputHandler = new DustedInputHandler());
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
