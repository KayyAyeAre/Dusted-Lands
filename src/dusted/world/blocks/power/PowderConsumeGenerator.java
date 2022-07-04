package dusted.world.blocks.power;

import arc.math.*;
import arc.util.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.power.*;
import mindustry.world.consumers.*;

public class PowderConsumeGenerator extends ConsumeGenerator implements PowderBlockc {
    public boolean[] powderFilter = {};
    public float powderCapacity = 20f;

    public ConsumePowderFilter filterPowder;

    public PowderConsumeGenerator(String name) {
        super(name);
    }

    @Override
    public void init() {
        powderFilter = new boolean[Vars.content.getBy(ContentType.effect_UNUSED).size];
        filterPowder = findConsumer(c -> c instanceof ConsumePowderFilter);
        super.init();
    }

    @Override
    public boolean[] powderFilters() {
        return powderFilter;
    }

    @Override
    public float powderCapacity() {
        return powderCapacity;
    }

    public class PowderConsumeGeneratorBuild extends ConsumeGeneratorBuild implements PowderBuildc {
        public PowderModule powders = new PowderModule();

        @Override
        public void updateEfficiencyMultiplier() {
            float m = filterPowder.efficiencyMultiplier(this);
            if (m > 0) efficiencyMultiplier = m;
        }

        @Override
        public void updateTile() {
            //literally the exact same method but with the check for hasItems removed
            boolean valid = efficiency > 0;

            warmup = Mathf.lerpDelta(warmup, valid ? 1f : 0f, 0.05f);

            productionEfficiency = efficiency * efficiencyMultiplier;
            totalTime += warmup * Time.delta;

            if (valid && Mathf.chanceDelta(effectChance)) {
                generateEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
            }

            if (valid && generateTime <= 0f) {
                consume();
                consumeEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
                generateTime = 1f;
            }

            if (liquidOutput != null) {
                float added = Math.min(productionEfficiency * delta() * liquidOutput.amount, liquidCapacity - liquids.get(liquidOutput.liquid));
                liquids.add(liquidOutput.liquid, added);
                dumpLiquid(liquidOutput.liquid);
            }

            generateTime -= delta() / itemDuration;
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }
    }
}
