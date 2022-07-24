package dusted.content;

import arc.graphics.*;
import dusted.type.weather.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.meta.*;

//TODO why does this still exist
public class DustedWeathers {
    public static Weather dustStorm, cavnenHaze;

    public static void load() {
        dustStorm = new ParticleWeather("dust-storm") {{
            color = noiseColor = Color.valueOf("8a6154");
            drawNoise = true;
            particleRegion = "particle";
            sizeMax = 6f;
            sizeMin = 1.6f;
            density = 1600f;

            force = 0.2f;
            attrs.set(Attribute.light, -0.25f);

            sound = Sounds.windhowl;
            soundVol = 0f;
            soundVolOscMag = 1.5f;
            soundVolOscScl = 1000f;
            soundVolMin = 0.02f;
        }};

        cavnenHaze = new BlindingWeather("cavnen-haze") {{
            color = Color.valueOf("545b50");
            attrs.set(Attribute.light, -0.65f);
        }};

        //TODO create weather for volcan areas
    }
}
