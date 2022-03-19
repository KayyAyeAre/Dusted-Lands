package dusted.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import dusted.world.interfaces.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;

public class DrawPowderRotator extends DrawBlock {
    public TextureRegion powder, rotator;

    @Override
    public void draw(GenericCrafterBuild entity) {
        Draw.rect(entity.block.region, entity.x, entity.y);

        if (entity instanceof PowderBlockc build) {
            Draw.color(build.powderModule().current() == null ? Color.clear : build.powderModule().current().color, build.powderModule().currentAmount() / build.powderCapacity());
            Draw.rect(powder, entity.x, entity.y);
            Draw.color();
        }

        Draw.rect(rotator, entity.x, entity.y, entity.totalProgress * 2f);
    }

    @Override
    public void load(Block block) {
        super.load(block);
        rotator = Core.atlas.find(block.name + "-rotator");
        powder = Core.atlas.find(block.name + "-powder");
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{block.region, rotator};
    }
}
