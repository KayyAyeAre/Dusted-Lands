package dusted.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import dusted.world.blocks.production.*;
import dusted.world.blocks.production.PowderCrafter.*;
import dusted.world.interfaces.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;

public class DrawPowder extends DrawBlock {
    public TextureRegion powderRegion;

    @Override
    public void draw(Building build) {
        if (build instanceof PowderCrafterBuild pbuild) {
            Draw.color(((PowderCrafter) pbuild.block).outputPowder.powder.color);
            Draw.alpha(pbuild.powders.get(((PowderCrafter) pbuild.block).outputPowder.powder) / ((PowderBlockc) build.block).powderCapacity());
            Draw.rect(powderRegion, build.x, build.y);
            Draw.color();
        }
    }

    @Override
    public void load(Block block) {
        powderRegion = Core.atlas.find(block.name + "-powder");
    }
}
