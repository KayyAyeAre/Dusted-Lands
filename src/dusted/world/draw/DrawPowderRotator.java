package dusted.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import dusted.world.interfaces.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class DrawPowderRotator extends DrawBlock {
    public TextureRegion powderRegion, rotatorRegion, topRegion;

    @Override
    public void draw(GenericCrafterBuild entity) {
        Draw.rect(entity.block.region, entity.x, entity.y);

        if (entity instanceof PowderBlockc build) {
            Draw.color(build.powderModule().current() == null ? Color.clear : build.powderModule().current().color, build.powderModule().currentAmount() / build.powderCapacity());
            Draw.rect(powderRegion, entity.x, entity.y);
            Draw.color();
        }


        Drawf.spinSprite(rotatorRegion, entity.x, entity.y, entity.totalProgress * 2f);
        Draw.rect(topRegion, entity.x, entity.y);
    }

    @Override
    public void load(Block block) {
        super.load(block);
        rotatorRegion = Core.atlas.find(block.name + "-rotator");
        powderRegion = Core.atlas.find(block.name + "-powder");
        topRegion = Core.atlas.find(block.name + "-top");
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{block.region, rotatorRegion, topRegion};
    }
}
