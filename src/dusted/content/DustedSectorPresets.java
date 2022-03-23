package dusted.content;

import mindustry.type.*;

public class DustedSectorPresets {
    public static SectorPreset dormantChasm;

    public static void load() {
        dormantChasm = new SectorPreset("dormant-chasm", DustedPlanets.krakai, 56) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 3;
        }};
    }
}
