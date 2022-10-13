package dusted.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

public class DrawDeterioration extends DrawBlock {
    public Color color = Color.valueOf("a0cd9e");
    public int particles = 10, subParticles = 4;
    public float alpha = 0.85f;
    public float particleLife = 80f, particleRadius = 2f, particleLen = 10f, subParticleLen = 2f;

    @Override
    public void draw(Building build) {
        float base = (Time.time / particleLife);
        rand.setSeed(build.id);

        Draw.color(color, alpha * build.warmup());
        Draw.blend(Blending.additive);
        for (int i = 0; i < particles; i++) {
            float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
            float angle = rand.random(360f);
            float len = particleLen * Interp.pow2Out.apply(fin);

            Angles.randLenVectors(build.id + i, subParticles, Interp.pow2Out.apply(fin) * subParticleLen, (x, y) -> {
                Fill.circle(build.x + Angles.trnsx(angle, len) + x, build.y + Angles.trnsy(angle, len) + y, fout * particleRadius);
            });
        }
        Draw.blend();
        Draw.color();
    }
}
