package dusted;

import dusted.content.*;
import mindustry.mod.*;

public class DustedLands extends Mod {
    @Override
    public void loadContent() {
        DustedStatusEffects.load();
        Powders.load();
        DustedBullets.load();
        DustedBlocks.load();
    }
}
