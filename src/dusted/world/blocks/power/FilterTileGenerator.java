package dusted.world.blocks.power;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

//copy of thermal gen which uses floatf instead of attribute
public class FilterTileGenerator extends PowerGenerator {
    public float radius = 60f * 8f;

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

        clipSize = Math.max(clipSize, radius * 2f);

        super.init();
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.powerRange, radius / Vars.tilesize, StatUnit.blocks);

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

        Drawf.dashCircle(x * Vars.tilesize, y * Vars.tilesize, radius, Pal.accent);
        drawPlaceText(Core.bundle.formatFloat("bar.efficiency", (Vars.world.tile(x, y) != null ? Vars.world.tile(x, y).getLinkedTilesAs(this, tempTiles).sumf(other -> filter.get(other.floor())) : 0f) * 100f, 1), x, y, valid);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return tile.getLinkedTilesAs(this, tempTiles).sumf(other -> filter.get(other.floor())) > 0f;
    }

    public class FilterTileGeneratorBuild extends GeneratorBuild implements TransferPowerc {
        public int lastChange = -2;
        public float eff;

        @Override
        public float warmup() {
            return Mathf.clamp(productionEfficiency);
        }

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

            updateTransfer();
        }

        @Override
        public void drawLight() {
            Drawf.light(x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }

        @Override
        public void draw() {
            super.draw();
            drawTransfer();
        }

        @Override
        public void onProximityAdded() {
            super.onProximityAdded();

            eff = tile.getLinkedTiles(tempTiles).sumf(other -> filter.get(other.floor()));
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public float radius() {
            return radius;
        }

        @Override
        public int lastChange() {
            return lastChange;
        }

        @Override
        public void lastChange(int change) {
            lastChange = change;
        }
    }
}
