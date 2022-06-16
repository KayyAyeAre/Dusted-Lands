package dusted.world.blocks.powder;

import arc.*;
import arc.graphics.g2d.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.world.*;

public class ArmoredChute extends Chute {
    public TextureRegion capRegion;

    public ArmoredChute(String name) {
        super(name);
        leaks = false;
    }

    @Override
    public void load() {
        super.load();
        capRegion = Core.atlas.find(name + "-cap");
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
        return (Vars.world.build(otherx, othery) instanceof PowderBuildc pow && pow.outputsPowder() && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
                (lookingAt(tile, rotation, otherx, othery, otherblock));
    }

    public class ArmoredChuteBuild extends ChuteBuild {
        @Override
        public void draw() {
            super.draw();
            //copied straight from armored conduit code lmao
            Building next = front();
            if (next != null && next.team == team && next instanceof PowderBuildc) return;

            Draw.rect(capRegion, x, y, rotdeg());
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return super.acceptPowder(source, powder) && (tile == null || source.block instanceof Chute ||
                    source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
        }
    }
}
