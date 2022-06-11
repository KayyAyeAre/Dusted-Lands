package dusted.world.blocks.powder;

import arc.*;
import arc.graphics.g2d.*;
import dusted.type.*;
import mindustry.gen.*;

public class PowderRouter extends PowderBlock {
    public TextureRegion powderRegion, topRegion;

    public PowderRouter(String name) {
        super(name);
        hasPower = true;
    }

    @Override
    public void load() {
        super.load();
        powderRegion = Core.atlas.find(name + "-powder");
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{region, topRegion};
    }

    public class PowderRouterBuild extends PowderBuild {
        @Override
        public void updateTile() {
            dumpPowder(powders.current());
        }

        @Override
        public void draw() {
            super.draw();
            Draw.color(powders.current().color, powders.currentAmount() / powderCapacity);
            Draw.rect(powderRegion, x, y);
            Draw.color();
            Draw.rect(topRegion, x, y);
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return (powders.current() == powder || powders.currentAmount() < 0.2f);
        }
    }
}
