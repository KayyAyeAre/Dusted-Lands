package dusted.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import dusted.graphics.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

//kinda just a copy of hoverpart
public class DrawSquarePulse extends DrawBlock {
    public Color pulseColor = DustedPal.lightCrisalt;
    public int squares = 3;
    public float stroke = 1.5f, radius = 8f, gtime = 80f, rotation = 45f;

    @Override
    public void draw(Building build) {
        Draw.color(pulseColor);

        for (int i = 0; i < squares; i++) {
            float fin = (build.totalProgress() / gtime + (float) i / squares) % 1f;
            Lines.stroke((1f - fin) * stroke * build.warmup());
            Lines.square(build.x, build.y, radius * fin, rotation);
        }

        Draw.color();
    }
}
