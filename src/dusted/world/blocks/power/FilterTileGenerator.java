package dusted.world.blocks.power;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

//copy of thermal gen which uses floatf instead of attribute
public class FilterTileGenerator extends TransferPowerGenerator {
    public Floatf<Floor> filter;
    public Effect generateEffect = Fx.none;
    public float effectChance = 0.05f;
    public @Nullable LiquidStack outputLiquid;

    public FilterTileGenerator(String name) {
        super(name);
    }

    @Override
    public void init() {
        if (outputLiquid != null) {
            outputsLiquid = true;
            hasLiquids = true;
        }

        super.init();
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.tiles, DustedStatValues.blocksFilter(filter, floating, size * size, false));
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60f, StatUnit.powerSecond);

        if (outputLiquid != null) {
            stats.add(Stat.output, StatValues.liquid(outputLiquid.liquid, outputLiquid.amount * size * size * 60f, true));
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        drawPlaceText(Core.bundle.formatFloat("bar.efficiency", Vars.world.tile(x, y).getLinkedTilesAs(this, tempTiles).sumf(other -> filter.get(other.floor())) * 100f, 1), x, y, valid);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return tile.getLinkedTilesAs(this, tempTiles).sumf(other -> filter.get(other.floor())) > 0f;
    }

    public class FilterTileGeneratorBuild extends TransferPowerGeneratorBuild {
        public float eff;

        @Override
        public void updateTile() {
            super.updateTile();

            productionEfficiency = enabled ? eff : 0f;

            if (productionEfficiency > 0.1f && Mathf.chanceDelta(effectChance)) {
                generateEffect.at(x + Mathf.range(3f), y + Mathf.range(3f));
            }

            if (outputLiquid != null) {
                float added = Math.min(productionEfficiency * delta() * outputLiquid.amount, liquidCapacity - liquids.get(outputLiquid.liquid));
                liquids.add(outputLiquid.liquid, added);
                dumpLiquid(outputLiquid.liquid);
            }
        }

        @Override
        public void drawLight() {
            Drawf.light(x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }

        @Override
        public void onProximityAdded() {
            super.onProximityAdded();

            eff = tile.getLinkedTiles(tempTiles).sumf(other -> filter.get(other.floor()));
        }
    }
}
