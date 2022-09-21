package dusted.world.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.type.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

public class DrawPowderAbsorb extends DrawBlock {
    public Powder powder;
    public int particles = 40;
    public float particleLen = 6f;
    public float minParticleRadius = 1f, maxParticleRadius = 2.5f;
    public DrawPowderAbsorb(Powder powder) {
        this.powder = powder;
    }

    @Override
    public void draw(Building build) {
        rand.setSeed(build.id);
        Draw.color(powder.color, 0.4f);
        Draw.blend(Blending.additive);
        for (int i = 0; i < particles; i++) {
            float fin = Mathf.curve(build.progress(), rand.random(1f));
            float angle = rand.random(360f);
            float len = rand.random(particleLen) * (1f - fin);
            float radius = Interp.pow2Out.apply(fin) * rand.random(minParticleRadius, maxParticleRadius);

            Fill.circle(build.x + Angles.trnsx(angle, len), build.y + Angles.trnsy(angle, len), radius);
        }

        Draw.color();
        Draw.blend();
    }
}
