package dusted.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.graphics.*;
import dusted.type.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.*;

public class DustedFx {
    public static Effect
    powderLeak = new Effect(46f, 20f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Mathf.lerpDelta(Layer.block - 0.001f, Layer.blockUnder, e.finpow()));
        color(powder.color, Color.gray, e.finpow() / 2f);

        randLenVectors(e.id, 10, e.finpow() * 10f, e.rotation, 22f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 2f);
        });
    }),

    smallBounce = new Effect(40f, 120f, e -> {
        if (!(e.data instanceof Float distance)) return;
        for (int i : Mathf.signs) {
            Draw.color(Pal.accent);
            Drawf.tri(e.x, e.y, distance, e.foutpow() * 8f, e.rotation + 90 * i);
            Draw.color();
            Drawf.tri(e.x, e.y, distance, e.foutpow() * 4f, e.rotation + 90 * i);
        }
    }),

    shootCavnenShrapnel = new Effect(20f, e -> {
        color(DustedPal.cavnenYellow, DustedPal.cavnenYellowBack, e.fin());
        Lines.stroke(e.fout() + 0.5f);

        randLenVectors(e.id, 5, 20f * e.finpow(), e.rotation, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 4f + 1f);
        });
    }),

    orbSummon = new Effect(40f, e -> {
        color(DustedPal.cavnenYellow, DustedPal.cavnenYellowBack, e.fin());
        randLenVectors(e.id, 12, e.finpow() * 12f, e.rotation, 360f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 2f);
        });
    }),

    stasis = new Effect(20f, e -> {
        color(Color.white, Pal.lancerLaser, e.fin());
        Lines.stroke(e.foutpow() * 3f);
        randLenVectors(e.id, 8, e.finpow() * 16f, e.rotation, 360f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.foutpow() * 3f);
        });

        Lines.stroke(e.foutpow() * 4f);
        Lines.circle(e.x, e.y, e.finpow() * 12f);
    }),

    shootTitaniumSpray = new Effect(20f, 70f, e -> {
        color(Pal.orangeSpark, DustedPal.lightTitanium, Color.gray, e.fin());

        randLenVectors(e.id, 16, e.finpow() * 60f, e.rotation, 26f, (x, y) -> {
            Fill.rect(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f, 0.65f + e.fout() * 1.6f, e.rotation + e.fin() * 180);
        });
    }),

    hitTitaniumSpray = new Effect(16f, e -> {
        color(Pal.orangeSpark, DustedPal.lightTitanium, e.fin());
        Fill.rect(e.x, e.y, 0.2f + e.fout() * 1.2f, 0.2f + e.fout() * 1.2f, e.rotation);
    }),

    shootLine = new Effect(25f, 80f, e -> {
        color(DustedPal.cavnenYellow, DustedPal.cavnenYellowBack, DustedPal.darkCavnen, e.fin());

        for (int i : Mathf.signs) {
            Drawf.tri(e.x + Angles.trnsx(e.rotation, 37.5f), e.y + Angles.trnsy(e.rotation, 37.5f), 100f, e.foutpow() * 8f, e.rotation + 90 * i);
        }
    }),

    shootQuartzFlame = new Effect(20f, 90f, e -> {
        color(DustedPal.lightQuartz, DustedPal.darkQuartz, e.fin());

        randLenVectors(e.id, 9, e.finpow() * 60f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.8f + e.fout() * 1.5f);
        });
    }),

    hitQuartzFlame = new Effect(14f, e -> {
        color(DustedPal.lightQuartz, DustedPal.darkQuartz, e.fin());
        Lines.stroke(0.7f + e.fout());

        randLenVectors(e.id, 4, 1f + e.fin() * 15f, e.rotation, 40f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 3 + 2.6f);
        });
    });
}
