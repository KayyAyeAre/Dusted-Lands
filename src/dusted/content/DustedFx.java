package dusted.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.game.Decay.*;
import dusted.graphics.*;
import dusted.type.*;
import dusted.world.blocks.environment.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.*;

public class DustedFx {
    public static final Rand rand = new Rand();

    public static Effect
    powderLeak = new Effect(46f, 20f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Mathf.lerpDelta(Layer.block - 0.001f, Layer.blockUnder, e.finpow()));
        rand.setSeed(e.id);

        randLenVectors(e.id, 10, e.finpow() * 10f, e.rotation, 22f, (x, y) -> {
            color(Tmp.c1.set(powder.color).mul(rand.random(1f, 1.1f)), Tmp.c2.set(powder.color).mul(rand.random(0.4f, 0.6f)), e.finpow() / 2f);
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 2f);
        });
    }),

    auraFade = new Effect(30f, e -> {
        color(e.color);
        Lines.dashCircle(e.x, e.y, e.rotation * e.fout(Interp.pow2));
    }),

    shieldFade = new Effect(60f, e -> {
        if (!(e.data instanceof DecayShield shield)) return;
        e.lifetime = shield.radius * 0.8f;

        Draw.z(Layer.shields + 2.5f);
        Draw.color(shield.team().color);

        if (Vars.renderer.animateShields) {
            Fill.poly(e.x, e.y, 24, shield.radius * e.foutpow(), Time.time / 10f);
        } else {
            Lines.stroke(1.5f);
            Draw.alpha(0.09f);
            Fill.poly(e.x, e.y, 24, shield.radius * e.foutpow(), Time.time / 10f);
            Draw.alpha(1f);
            Lines.poly(e.x, e.y, 24, shield.radius * e.foutpow(), Time.time / 10f);
        }
    }),

    riftGlow = new Effect(180f, e -> {
        if (!(e.data instanceof Tile tile) || !(tile.floor() instanceof RiftFloor floor)) return;
        int variant = Mathf.randomSeed(tile.pos(), 0, Math.max(0, floor.variants - 1));

        Draw.color(DustedPal.decayingYellow, e.fslope() * e.fslope());
        Draw.rect(floor.riftRegions[variant], tile.worldx(), tile.worldy());
        Draw.rect(floor.riftGlowRegions[variant], tile.worldx(), tile.worldy());
    }).layer(Layer.floor + 0.01f),

    deteriorating = new Effect(25f, e -> {
        color(DustedPal.decay, DustedPal.darkerDecay, e.fin());

        randLenVectors(e.id, 3, e.finpow() * 6f + 2f, (x, y) -> {
            randLenVectors(e.id, 4, e.finpow() * 2f, e.rotation, 15f, (ix, iy) -> {
                Fill.circle(e.x + x + ix, e.y + y + iy, e.fout() * 2f);
            });
        });
    }).layer(Layer.effect + 0.021f),

    yellowDeteriorating = new Effect(20f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());

        randLenVectors(e.id, 3, e.finpow() * 6f + 2f, (x, y) -> {
            randLenVectors(e.id, 4, e.finpow() * 2f, e.rotation, 15f, (ix, iy) -> {
                Fill.circle(e.x + x + ix, e.y + y + iy, e.fout() * 2f);
            });
        });
    }),

    mist = new Effect(240f, e -> {
        color(DustedPal.mist, e.fslope() * e.fslope() * 0.2f);

        randLenVectors(e.id, 2, e.finpow() * 8f, e.finpow() * 14f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 5f + e.fout() * 2f);
        });
    }),

    blazing = new Effect(40f, e -> {
        color(DustedPal.lightOrchar, DustedPal.darkOrchar, e.fin());

        randLenVectors(e.id, 5, 3f + e.fin() * 9f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
        });
        float w = 1f + e.fout() * 3f;
        randLenVectors(e.id, 3, 5f, (x, y) -> {
            Drawf.tri(e.x + x, e.y + y, w, 4f * e.fout(), Mathf.angle(x, y));
            Drawf.tri(e.x + x, e.y + y, w, 2f * e.fout(), Mathf.angle(x, y) + 180f);
        });
    }),

    quartzBurn = new Effect(25f, e -> {
        color(DustedPal.lightQuartz, DustedPal.darkQuartz, e.fin());

        randLenVectors(e.id, 3, e.finpow() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
        });
    }),

    funnelExtractSmall = new Effect(30f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Layer.blockOver);
        color(powder.color);

        for (int i = 0; i < 4; i++) {
            randLenVectors(e.id, 6, e.finpow() * 4f, 45f + i * 90f, 10f, (x, y) -> {
                Fill.circle(e.x + x + Angles.trnsx(Mathf.angle(x, y), 3f), e.y + y + Angles.trnsy(Mathf.angle(x, y), 3f), e.fout() * 1.2f);
            });
        }
    }),

    funnelExtract = new Effect(35f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Layer.blockOver);
        color(powder.color);

        for (int i = 0; i < 4; i++) {
            randLenVectors(e.id, 8, e.finpow() * 6f, 45f + i * 90f, 15f, (x, y) -> {
                Fill.circle(e.x + x + Angles.trnsx(Mathf.angle(x, y), 6f), e.y + y + Angles.trnsy(Mathf.angle(x, y), 6f), e.fout() * 2f);
            });
        }
    }),

    bounce = new Effect(40f, 120f, e -> {
        if (!(e.data instanceof Float distance)) return;
        float tx = e.x + Angles.trnsx(e.rotation + 180f, distance / 2) + Angles.trnsx(e.rotation, e.finpow() * distance);
        float ty = e.y + Angles.trnsy(e.rotation + 180f, distance / 2) + Angles.trnsy(e.rotation, e.finpow() * distance);
        float width = distance / 4f;
        float height = distance / 10f;

        for (int i : Mathf.signs) {
            color(DustedPal.decayingYellow);
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

    quartzSmokeCloud = new Effect(70f, e -> {
        color(DustedPal.lightQuartz, e.fslope() * 0.6f);

        randLenVectors(e.id, 12, e.finpow() * 18f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 7f);
        });
    }),

    crisaltSmoke = new Effect(70f, e -> {
        color(DustedPal.lightCrisalt, e.fout());

        randLenVectors(e.id, 4, e.fin() * 14f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.finpow() * 5f);
        });
    }),

    mineIgnite = new Effect(50f, e -> {
        color(Pal.lightFlame, Pal.darkFlame, Color.gray, e.fin());
        randLenVectors(e.id, 10, e.finpow() * 18f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2.5f);
        });
    }),

    sliceSparks = new Effect(20f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());
        Lines.stroke(1.5f);
        randLenVectors(e.id, 2, e.finpow() * 14f, e.rotation + 180f, 10f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 9f);
        });
    }),

    flameSpreadColor = new Effect(30f, e -> {
        color(e.color);

        randLenVectors(e.id, 14, e.finpow() * 12f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f);
        });
    }),

    shootBlazingSpread = new Effect(35f, e -> {
        color(DustedPal.blazingRed, DustedPal.darkBlazingRed, e.fin());

        randLenVectors(e.id, 25, e.finpow() * 35f, e.rotation, 60f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f);
        });
    }),

    hitBlazingSpread = new Effect(14, e -> {
        color(DustedPal.blazingRed, DustedPal.darkBlazingRed, e.fin());
        Lines.stroke(e.fout() * 2f);

        randLenVectors(e.id, 3, 1f + e.fin() * 15f, e.rotation, 60f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 4f);
        });
    }),

    shootLaunch = new Effect(40f, e -> {
        e.scaled(20f, i -> {
            color(Pal.lighterOrange, Pal.lightOrange, Pal.gray, i.fin());
            Lines.stroke(i.fout() * 2f);

            randLenVectors(i.id, 6, i.finpow() * 12f, i.rotation, 20f, (x, y) -> {
                Lines.lineAngle(i.x + x, i.y + y, Mathf.angle(x, y), i.foutpow() * 7f);
            });
        });

        color(Pal.lightishGray, Pal.gray, e.fin());

        randLenVectors(e.id, 8, e.finpow() * 9f, e.rotation + 180f, 50f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
        });
    }),

    hitLaunch = new Effect(20f, e -> {
        color(Color.white, Pal.lightOrange, e.fin());
        Lines.stroke(e.fout() * 2f);

        randLenVectors(e.id, 6, e.finpow() * 9f, e.rotation + 180f, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.foutpow() * 5f);
        });
        randLenVectors(e.id, 8, e.finpow() * 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 3f);
        });

        Lines.circle(e.x, e.y, e.finpow() * 4f);
    }),

    shootCrushQuartz = new Effect(65f, e -> {
        e.scaled(20f, i -> {
            color(DustedPal.lightQuartz, DustedPal.darkQuartz, i.fin());
            Lines.stroke(2f);
            randLenVectors(i.id, 8, i.finpow() * 14f, i.rotation + 180f, 35f, (x, y) -> {
                Lines.lineAngle(i.x + x, i.y + y, Mathf.angle(x, y), i.fout() * 7f);
            });
        });

        color(DustedPal.lightQuartz, DustedPal.darkQuartz, e.fin());
        randLenVectors(e.id, 14, e.finpow() * 35f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 7f);
        });
    }),

    hitCrushQuartz = new Effect(70f, e -> {
        e.scaled(35f, i -> {
            color(DustedPal.lightQuartz, DustedPal.darkQuartz, i.fin());
            randLenVectors(i.id, 16, i.finpow() * 65f, i.rotation, 50f, (x, y) -> {
                Fill.circle(i.x + x, i.y + y, i.fout() * 14f);
            });

            Lines.stroke(i.fout() * 2.5f);
            Lines.circle(i.x, i.y, i.finpow() * 40f);
        });

        color(DustedPal.darkQuartz, 0.8f);
        randLenVectors(e.id, 8, e.finpow() * 45f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpowdown() * 9f);
        });
    }),

    pushQuartz = new Effect(18f, e -> {
        color(DustedPal.darkQuartz);
        randLenVectors(e.id, 12, e.finpow() * 26f, e.rotation, 25f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
        });
    }),

    shootBlastSpray = new Effect(35f, e -> {
        color(e.color);
        randLenVectors(e.id, 20, e.finpow() * 60f, e.rotation, 25f, (x, y) -> {
            randLenVectors(e.id, 4, e.finpow() * 12f, (ix, iy) -> {
                Fill.circle(e.x + x + ix, e.y + y + iy, e.fout() * 2f);
            });
        });
    }),

    //its just two effects with diferent colors..
    shootCavnenShrapnel = new Effect(20f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());
        Lines.stroke(e.fout() + 0.5f);

        randLenVectors(e.id, 5, 20f * e.finpow(), e.rotation, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 4f + 1f);
        });
    }),

    shootPinkShrapnel = new Effect(20f, e -> {
        color(Color.white, DustedPal.pinkHeal, e.fin());
        Lines.stroke(e.fout() + 0.5f);

        randLenVectors(e.id, 5, 20f * e.finpow(), e.rotation, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 4f + 1f);
        });
    }),

    shootCavnenSmoke = new Effect(50f, e -> {
        color(DustedPal.decayingYellow, e.foutpowdown() * 0.88f);

        randLenVectors(e.id, 7, 30f * e.finpow(), e.rotation, 40f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpowdown() * 5f);
        });
    }),

    splitShot = new Effect(40f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());

        randLenVectors(e.id, 7, 18f * e.fin(), e.rotation, 40f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 4f + 2f);
        });
    }),

    orbSummon = new Effect(40f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());
        randLenVectors(e.id, 12, e.finpow() * 12f, e.rotation, 360f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 2f);
        });
    }),

    shootPowder = new Effect(16f, e -> {
        color(Color.white, e.color, e.fin());
        float w = 1.4f + 6 * e.fout();
        Drawf.tri(e.x, e.y, w, 18f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
    }),

    shootPowderSquares = new Effect(40f, e -> {
        color(Color.white, e.color, e.fin());
        randLenVectors(e.id, 18, e.finpow() * 18f, e.rotation, 25f, (x, y) -> {
            Fill.rect(e.x + x, e.y + y, e.foutpow() * 6f, e.foutpow() * 6f, Mathf.angle(x, y) + e.fin() * 60f);
        });
    }),

    hitPowder = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        e.scaled(10f, s -> {
            Lines.stroke(s.fout() * 2.5f);
            Lines.circle(s.x, s.y, s.finpow() * 6f);
        });

        randLenVectors(e.id, 5, e.finpow() * 8f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f);
        });
    }),

    hitBlastSpray = new Effect(25f, e -> {
        color(e.color);
        e.scaled(15f, i -> {
            Lines.stroke(i.fout() * 2f);
            Lines.circle(i.x, i.y, i.finpow() * 6f + 1f);
        });
        randLenVectors(e.id, 3, e.finpow() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
        });
    }),

    hitCavnen = new Effect(15f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());

        e.scaled(10f, s -> {
            Lines.stroke(s.fout() * 2.5f);
            Lines.circle(s.x, s.y, s.finpow() * 6f);
        });

        randLenVectors(e.id, 5, e.finpow() * 8f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 5f);
        });
    }),

    hitPinkLaser =  new Effect(10f, e -> {
        color(Color.white, DustedPal.pinkHeal, e.fin());
        stroke(0.6f + e.fout());
        Lines.circle(e.x, e.y, e.fin() * 4f);

        randLenVectors(e.id, 5, 1f + e.finpow() * 6f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.5f + e.fout());
        });
    }),

    //kinda lazy lmao
    pinkHeal = new Effect(11, e -> {
        color(DustedPal.pinkHeal);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, 2f + e.finpow() * 7f);
    }),

    pinkHealWaveDynamic = new Effect(22f, e -> {
        color(DustedPal.pinkHeal);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, 4f + e.finpow() * e.rotation);
    }),

    //jkdfhgkjshrgiushdjkfckvhiu
    high = new Effect(20f, e -> {
        randLenVectors(e.id, 2, 4f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y + e.finpow(), e.fout() * 2f);
        });
    });
}
