package dusted.content;

import mindustry.type.*;

public class DustedSectorPresets {
    public static SectorPreset outbreak, taintedValley;

    public static void load() {
        outbreak = new SectorPreset("outbreak", DustedPlanets.krakai, 269) {{
            alwaysUnlocked = true;
            captureWave = 5;
            difficulty = 1;
        }};

        taintedValley = new SectorPreset("tainted-valley", DustedPlanets.krakai, 53) {{
            captureWave = 10;
            difficulty = 2;
        }};
    }
}
