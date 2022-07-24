package dusted.world.blocks.defense;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import dusted.*;
import dusted.game.Decay.*;
import dusted.graphics.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class DecaySuppressor extends Block {
    public float radius = 160f;
    public TextureRegion topRegion;
    public Color topColor = DustedPal.pinkHeal;

    public DecaySuppressor(String name) {
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.projectors;
    }

    @Override
    public void load() {
        super.load();

        topRegion = Core.atlas.find(name + "-top");
    }

    public class SuppressorBuild extends Building {
        public float heat;
        public DecayShield shield;

        @Override
        public void created() {
            Core.app.post(() -> {
                shield = new DecayShield(() -> Tmp.v1.set(this), () -> team, () -> radius);
                DustedLands.decay.shields.add(shield);
            });
        }

        @Override
        public void onRemoved() {
            DustedLands.decay.shields.remove(shield);
        }

        @Override
        public void updateTile() {
            heat = Mathf.lerpDelta(heat, efficiency, 0.05f);
        }

        public float realRadius() {
            return radius * heat;
        }

        @Override
        public void draw() {
            super.draw();

            Draw.color(topColor);
            Draw.alpha(heat * Mathf.absin(12f, 1f) * 0.5f);
            Draw.rect(topRegion, x, y);
            Draw.reset();

            drawSuppressor();
        }

        public void drawSuppressor() {
            Draw.z(Layer.shields + 2.5f);
            Draw.color(team.color);

            if (Vars.renderer.animateShields) {
                Fill.poly(x, y, 6, realRadius());
            } else {
                Lines.stroke(1.5f);
                Draw.alpha(0.09f);
                Fill.poly(x, y, 6, realRadius());
                Draw.alpha(1f);
                Lines.poly(x, y, 6, realRadius());
            }
        }

        @Override
        public void write(Writes write) {
            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision) {
            heat = read.f();
        }
    }
}
