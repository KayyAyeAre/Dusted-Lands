package dusted.content;

import mindustry.type.*;

public class DustedSectorPresets {
    public static SectorPreset cariousOutset;

    public static void load() {
        //TODO maybe this could be renamed to "outbreak"
        cariousOutset = new SectorPreset("carious-outset", DustedPlanets.krakai, 269) {{
            alwaysUnlocked = true;
            captureWave = 5;
            difficulty = 1;
        }};
    }
}
