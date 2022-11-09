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
    public void init() {
        super.init();

        clipSize = Math.max(clipSize, radius * 2f);
    }

    @Override
    public void load() {
        super.load();

        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * Vars.tilesize, y * Vars.tilesize, radius, Pal.accent);
    }

    public class SuppressorBuild extends Building {
        public float heat;
        public DecayShield shield;

        @Override
        public void created() {
            Core.app.post(() -> {
                shield = new DecayShield(this);
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
            if (shield != null) shield.radius = realRadius();
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

            if (shield != null) shield.draw();
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.dashCircle(x, y, radius, Pal.accent);
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
