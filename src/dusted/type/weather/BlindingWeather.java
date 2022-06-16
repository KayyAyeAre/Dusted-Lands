package dusted.type.weather;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.type.*;

//TODO remove
public class BlindingWeather extends Weather {
    public Color color = Color.white.cpy();

    public BlindingWeather(String name) {
        super(name);
    }

    //lmao lazy code
    @Override
    public void drawOver(WeatherState state) {
        Draw.color(color, 0.3f + Mathf.absin(0.3f, 0.22f));
        Draw.rect();
    }
}
