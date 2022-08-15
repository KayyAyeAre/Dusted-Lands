package dusted.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;

public class DrawSideExtractor extends DrawBlock {
    public TextureRegion extractorRegion, smallExtractorRegion, glowRegion, smallGlowRegion;
    public float scl = 12f, mag = 0.8f;
    public Color glowColor = Color.valueOf("dbeaff");

    @Override
    public void draw(Building build) {
        float o1 = Mathf.sin(scl, mag) * build.warmup(), o2 = Mathf.sin(Time.time + 180f, scl, mag) * build.warmup();
        float a1 = Mathf.absin(scl, 0.5f) * build.warmup(), a2 = Mathf.absin(Time.time + 180f, scl, 0.5f) * build.warmup();

        Draw.rect(
                extractorRegion,
                build.x + Angles.trnsx(build.rotdeg() - 90, 0, build.hitSize() / 2f + o1),
                build.y + Angles.trnsy(build.rotdeg() - 90, 0, build.hitSize() / 2f + o1),
                build.rotdeg()
        );
        Draw.color(glowColor, a1);
        Draw.rect(
                glowRegion,
                build.x + Angles.trnsx(build.rotdeg() - 90, 0, build.hitSize() / 2f + o1),
                build.y + Angles.trnsy(build.rotdeg() - 90, 0, build.hitSize() / 2f + o1),
                build.rotdeg()
        );
        Draw.color();

        for (int i : Mathf.signs) {
            Draw.rect(
                    smallExtractorRegion,
                    build.x + Angles.trnsx(build.rotdeg() - 90, build.hitSize() / 4f * i, build.hitSize() / 2f + o2),
                    build.y + Angles.trnsy(build.rotdeg() - 90, build.hitSize() / 4f * i, build.hitSize() / 2f + o2),
                    build.rotdeg()
            );
            Draw.color(glowColor, a2);
            Draw.rect(
                    smallGlowRegion,
                    build.x + Angles.trnsx(build.rotdeg() - 90, build.hitSize() / 4f * i, build.hitSize() / 2f + o2),
                    build.y + Angles.trnsy(build.rotdeg() - 90, build.hitSize() / 4f * i, build.hitSize() / 2f + o2),
                    build.rotdeg()
            );
            Draw.color();
        }
    }

    @Override
    public void load(Block block) {
        extractorRegion = Core.atlas.find(block.name + "-extractor");
        smallExtractorRegion = Core.atlas.find(block.name + "-extractor-small");
        glowRegion = Core.atlas.find(block.name + "-extractor-glow");
        smallGlowRegion = Core.atlas.find(block.name + "-extractor-glow-small");
    }
}
