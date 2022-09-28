package dusted.content;

import mindustry.type.*;

public class DustedSectorPresets {
    public static SectorPreset outbreak, taintedValley, corruptedIslands, cariousBulwark;

    public static void load() {
        outbreak = new SectorPreset("outbreak", DustedPlanets.krakai, 269) {{
            alwaysUnlocked = true;
            captureWave = 5;
            difficulty = 1;
        }};

        taintedValley = new SectorPreset("tainted-valley", DustedPlanets.krakai, 37) {{
            captureWave = 10;
            difficulty = 2;
        }};

        corruptedIslands = new SectorPreset("corrupted-islands", DustedPlanets.krakai, 127) {{
            captureWave = 20;
            difficulty = 3;
        }};

        cariousBulwark = new SectorPreset("carious-bulwark", DustedPlanets.krakai, 24) {{
            difficulty = 4;
        }};
    }
}
