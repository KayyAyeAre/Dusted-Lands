package dusted.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import dusted.world.blocks.production.*;
import dusted.world.blocks.production.PowderCrafter.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class DrawPowderMixer extends DrawBlock {
    public TextureRegion bottomRegion, topRegion, powderRegion;

    @Override
    public void draw(GenericCrafterBuild build) {
        Draw.rect(bottomRegion, build.x, build.y);

        if (build instanceof PowderCrafterBuild pbuild) {
            Draw.color(((PowderCrafter) pbuild.block).outputPowder.powder.color);
            Draw.alpha(pbuild.powders.get(((PowderCrafter) pbuild.block).outputPowder.powder) / pbuild.powderCapacity());
            Draw.rect(powderRegion, build.x, build.y);
            Draw.color();
        }

        Draw.rect(topRegion, build.x, build.y);
    }

    @Override
    public void load(Block block) {
        topRegion = Core.atlas.find(block.name + "-top");
        bottomRegion = Core.atlas.find(block.name + "-bottom");
        powderRegion = Core.atlas.find(block.name + "-powder");
    }
}
