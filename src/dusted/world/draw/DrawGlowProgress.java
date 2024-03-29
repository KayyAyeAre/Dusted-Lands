package dusted.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;

public class DrawGlowProgress extends DrawBlock {
    public Color color;
    public TextureRegion glow;

    public DrawGlowProgress(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Building build) {
        float progress = build.progress() < 0.9f ? Mathf.curve(build.progress(), 0f, 0.9f) : Mathf.curve(1f - build.progress(), 0f, 0.1f);

        Draw.blend(Blending.additive);
        Draw.color(color, progress);
        Draw.rect(glow, build.x, build.y);
        Draw.blend();
        Draw.color();
    }

    @Override
    public void load(Block block) {
        glow = Core.atlas.find(block.name + "-glow");
    }
}
