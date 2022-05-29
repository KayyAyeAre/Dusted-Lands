package dusted.content;

import mindustry.type.*;

public class DustedSectorPresets {
    public static SectorPreset cariousOutset, decrepitHollows, ardentPeaks, meltingPoint;

    public static void load() {
        cariousOutset = new SectorPreset("carious-outset", DustedPlanets.krakai, 56) {{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 3;
        }};
    }
}
