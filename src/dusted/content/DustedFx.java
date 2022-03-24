package dusted.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.type.*;
import dusted.world.interfaces.PowderBlockc.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.*;

public class DustedFx {
    public static Effect

    powderLeak = new Effect(46f, 20f, e -> {
        if (!(e.data instanceof PowderEffectData pData)) return;
        Draw.z(Mathf.lerpDelta(Layer.block - 0.001f, Layer.blockUnder, e.finpow()));
        color(pData.powder.color, Color.gray, e.finpow() / 2f);

        randLenVectors(e.id, 10, e.finpow() * pData.amount, e.rotation, 22f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 2f);
        });
    }),

    shootTitaniumSpray = new Effect(20f, 70f, e -> {
        color(Pal.orangeSpark, Color.valueOf("8da1e3"), Color.gray, e.fin());

        randLenVectors(e.id, 16, e.finpow() * 60f, e.rotation, 26f, (x, y) -> {
            Fill.rect(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f, 0.65f + e.fout() * 1.6f, e.rotation + e.fin() * 180);
        });
    }),

    hitTitaniumSpray = new Effect(16f, e -> {
        color(Pal.orangeSpark, Color.valueOf("8da1e3"), e.fin());
        Fill.rect(e.x, e.y, 0.2f + e.fout() * 1.2f, 0.2f + e.fout() * 1.2f, e.rotation);
    });

    public static class PowderEffectData {
        public static PowderEffectData tmp = new PowderEffectData();
        public float amount;
        public Powder powder;

        public PowderEffectData set(Powder powder, float amount) {
            this.powder = powder;
            this.amount = amount;

            return this;
        }
    }
}
