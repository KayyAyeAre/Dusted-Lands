package dusted.world.blocks.production;

import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.meta.values.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

public class PowderCrafter extends GenericCrafter {
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;
    public PowderStack outputPowder;

    public PowderCrafter(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, new PowderValue(outputPowder.powder, outputPowder.amount * (60f / craftTime), true));
    }

    public class PowderCrafterBuild extends GenericCrafterBuild implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public boolean shouldConsume() {
            return !(powders.get(outputPowder.powder) >= powderCapacity - 0.001f) && enabled;
        }

        @Override
        public void updateTile() {
            if (consValid()) {
                progress += getProgressIncrease(craftTime);
                totalProgress += delta();
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);

                if (Mathf.chanceDelta(updateEffectChance)) {
                    updateEffect.at(getX() + Mathf.range(size * 4f), getY() + Mathf.range(size * 4));
                }
            } else {
                warmup = Mathf.lerp(warmup, 0f, 0.02f);
            }

            if (progress >= 1f) {
                consume();

                handlePowder(outputPowder.powder, outputPowder.amount);

                craftEffect.at(x, y);
                progress %= 1f;
            }

            dumpPowder(outputPowder.powder);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            powders.write(write);
        }

        @Override
        public void read(Reads read) {
            super.read(read);
            powders.read(read);
        }

        @Override
        public float powderCapacity() {
            return powderCapacity;
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public Bits filters() {
            return powderFilters;
        }
    }
}
