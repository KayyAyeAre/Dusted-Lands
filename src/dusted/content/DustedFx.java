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

    bounce = new Effect(40f, 120f, e -> {
        if (!(e.data instanceof Float distance)) return;
        float tx = e.x + Angles.trnsx(e.rotation + 180f, distance / 2) + Angles.trnsx(e.rotation, e.finpow() * distance);
        float ty = e.y + Angles.trnsy(e.rotation + 180f, distance / 2) + Angles.trnsy(e.rotation, e.finpow() * distance);
        float width = distance / 4f;
        float height = distance / 10f;

        for (int i : Mathf.signs) {
            Draw.color(DustedPal.cavnenYellow);
            Drawf.tri(e.x, e.y, distance + 22f, e.foutpow() * (distance / 16f), e.rotation + 90 * i);

            Fill.tri(tx + Angles.trnsx(e.rotation + 140f * i, e.foutpow() * width), ty + Angles.trnsy(e.rotation + 140f * i, e.foutpow() * width), tx + Angles.trnsx(e.rotation, e.foutpow() * height), ty + Angles.trnsy(e.rotation, e.foutpow() * height), tx - Angles.trnsx(e.rotation, e.foutpow() * height), ty - Angles.trnsy(e.rotation, e.foutpow() * height));
        }
    }),

    eruption = new Effect(80f, 1200f, e -> {
        color(Pal.lighterOrange, Pal.lightOrange, Color.gray, e.fin());
        randLenVectors(e.id, 32, 520f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 12f + 0.5f);
        });
        color(Color.gray);
        randLenVectors(e.id, 44, 580f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f);
        });
    }),

    shootCavnenShrapnel = new Effect(20f, e -> {
        color(DustedPal.cavnenYellow, DustedPal.cavnenYellowBack, e.fin());
        Lines.stroke(e.fout() + 0.5f);

        randLenVectors(e.id, 5, 20f * e.finpow(), e.rotation, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 4f + 1f);
        });
    }),

    bisectShoot = new Effect(40f, e -> {
        color(DustedPal.cavnenYellow, DustedPal.cavnenYellowBack, e.fin());

        randLenVectors(e.id, 7, 18f * e.fin(), e.rotation, 40f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 4f + 2f);
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

    shootQuartz = new Effect(16, e -> {
        color(DustedPal.lightQuartz, DustedPal.darkQuartz, e.fin());
        float w = 1.4f + 6 * e.fout();
        Drawf.tri(e.x, e.y, w, 18f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
    }),

    hitQuartz = new Effect(33f, e -> {
        color(DustedPal.lightQuartz, DustedPal.darkQuartz, e.fin());

        Lines.stroke(e.fout() * 2.5f);
        Lines.circle(e.x, e.y, e.finpow() * 6f);
        randLenVectors(e.id, 5, e.finpow() * 8f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f);
        });
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
