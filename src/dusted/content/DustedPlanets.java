package dusted.content;

import arc.graphics.*;
import dusted.maps.planet.*;
import dusted.world.meta.*;
import mindustry.*;
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
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 29, 0.56f, 0.16f, 5, Color.valueOf("fa9b5c").a(0.75f), 2, 0.55f, 1f, 0.35f),
                    new HexSkyMesh(this, 4, 0.2f, 0.18f, 5, Color.valueOf("ffc89e").a(0.75f), 2, 0.55f, 1.2f, 0.5f)
            );
            landCloudColor = Color.valueOf("fa9b5c");
            atmosphereColor = Color.valueOf("fa9b5c");
            iconColor = Color.valueOf("6d726b");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 269;
            defaultCore = DustedBlocks.coreAbate;
            defaultEnv = DustedEnv.volcanic | Env.terrestrial | Env.oxygen;

            //disabled for now, breaks transfer link configs
            prebuildBase = false;

            ruleSetter = r -> {
                r.attributes.set(Attribute.heat, 1.2f);
                r.attributes.set(DustedAttribute.decay, 0.5f);
            };

            hiddenItems.addAll(Vars.content.items()).removeAll(DustedItems.krakaiItems);

            unlockedOnLand.add(DustedBlocks.coreAbate);
        }};

        //i dont fucking know
        Planets.serpulo.hiddenItems.addAll(DustedItems.krakaiItems).removeAll(Items.serpuloItems);
        Planets.erekir.hiddenItems.addAll(DustedItems.krakaiItems).removeAll(Items.erekirOnlyItems);
    }
}
