package dusted.world.draw;

import arc.graphics.g2d.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

public class DrawPowder extends DrawBlock {
    public Powder drawPowder;
    public float padding;

    public DrawPowder(Powder drawPowder, float padding) {
        this.drawPowder = drawPowder;
        this.padding = padding;
    }

    @Override
    public void draw(Building build) {
        if (!(build instanceof PowderBuildc pbuild)) return;
        Draw.color(drawPowder.color, pbuild.powderModule().currentAmount() / ((PowderBlockc) build.block).powderCapacity());
        Fill.rect(build.x, build.y, build.hitSize() - padding * 2f, build.hitSize() - padding * 2f);
        Draw.color();
    }
}
