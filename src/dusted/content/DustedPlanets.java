package dusted.content;

import arc.graphics.*;
import dusted.maps.planet.*;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.world.meta.*;

public class DustedPlanets {
    public static Planet krakai;

    public static void load() {
        krakai = new Planet("krakai", Planets.sun, 1f, 3) {{
            alwaysUnlocked = true;
            generator = new KrakaiPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            atmosphereColor = Color.valueOf("a38772");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 269;
            defaultEnv = Env.scorching | Env.terrestrial | Env.oxygen;
        }};
    }
}
