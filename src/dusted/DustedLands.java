package dusted;

import dusted.content.*;
import mindustry.*;
import mindustry.mod.*;

public class DustedLands extends Mod {
    public DustedLands() {
        Vars.enableConsole = true;
    }

    @Override
    public void loadContent() {
        DustedStatusEffects.load();
        DustedItems.load();
        Powders.load();
        DustedBullets.load();
        DustedBlocks.load();
        DustedWeathers.load();
        //DustedPlanets.load();
        //DustedSectorPresets.load();
        DustedTechTree.load();
    }
}
