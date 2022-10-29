package dusted.world.blocks.units;

import dusted.world.blocks.units.UnitConstructor.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.units.UnitAssembler.*;
import mindustry.world.meta.*;

//aaaaaaaa
public class UnitConstructorModule extends UnitAssemblerModule {
    public int ptier = 0;

    public UnitConstructorModule(String name) {
        super(name);
    }

    @Override
    public UnitConstructorBuild getLink(Team team, int x, int y, int rotation) {
        var results = Vars.indexer.getFlagged(team, BlockFlag.unitAssembler).<UnitAssemblerBuild>as();

        return (UnitConstructorBuild) results.find(b -> b instanceof UnitConstructorBuild && b.moduleFits(this, x * Vars.tilesize + offset, y * Vars.tilesize + offset, rotation));
    }

    public class UnitConstructorModuleBuild extends UnitAssemblerModuleBuild {
        public int ptier() {
            return ptier;
        }

        @Override
        public void handleItem(Building source, Item item) {
            link.handleItem(this, item);
        }

        @Override
        public void handleLiquid(Building source, Liquid liquid, float amount) {
            link.handleLiquid(this, liquid, amount);
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return link.acceptItem(source, item);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return link.acceptLiquid(source, liquid);
        }
    }
}
