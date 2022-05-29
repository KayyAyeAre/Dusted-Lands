package dusted.world.blocks.powder;

import dusted.type.*;
import dusted.world.interfaces.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;

public class ArmoredChute extends Chute {
    public ArmoredChute(String name) {
        super(name);
        leaks = false;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
        return (otherblock instanceof PowderBlockc pow && pow.outputsPowder() && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
                (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock instanceof PowderBlockc);
    }

    public class ArmoredChuteBuild extends ChuteBuild {
        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return super.acceptPowder(source, powder) && (tile == null || source.block instanceof Chute ||
                    source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation);
        }
    }
}
