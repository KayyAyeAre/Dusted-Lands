package dusted.content;

import arc.graphics.*;
import dusted.type.weather.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weather.*;
import mindustry.world.meta.*;

public class DustedWeathers {
    public static Weather dustStorm, cavnenHaze, volcanicEruption;

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

        volcanicEruption = new EruptionWeather("volcanic-eruption") {{
            color = noiseColor = Color.valueOf("db401c");
            drawNoise = true;
            particleRegion = "particle";
            sizeMax = 16f;
            sizeMin = 6f;
            density = 1100f;

            sound = Sounds.windhowl;
            soundVol = 0f;
            soundVolOscMag = 1.5f;
            soundVolOscScl = 1000f;
            soundVolMin = 0.02f;

            attrs.set(Attribute.light, -0.25f);
            attrs.set(Attribute.heat, 0.5f);
        }};
    }
}
