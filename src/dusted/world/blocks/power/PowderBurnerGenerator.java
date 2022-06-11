package dusted.world.blocks.power;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import dusted.type.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.blocks.power.*;

public class PowderBurnerGenerator extends PowerGenerator {
    public float minFlammability = 0.3f;
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;
    public Effect generateEffect = Fx.generatespark;
    public Color heatColor = Color.valueOf("ff9b59");
    public float generateEffectRnd = 3f;
    public float maxPowderGenerate = 0.4f;
    public TextureRegion topRegion;

    public PowderBurnerGenerator(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void init() {
        super.init();
        ((CustomConsume) new ConsumePowderFilter(powder -> powder.flammability > minFlammability, maxPowderGenerate).update(false).optional(true, false)).add(consumes);
        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });
    }

    public class PowderBurnerGeneratorBuild extends GeneratorBuild implements PowderBlockc {
        public float heat;
        public PowderModule powders = new PowderModule();

        @Override
        public boolean productionValid() {
            return generateTime > 0;
        }

        @Override
        public void draw() {
            super.draw();
            Draw.color(heatColor);
            Draw.alpha(heat * 0.4f + Mathf.absin(Time.time, 8f, 0.6f) * heat);
            Draw.rect(topRegion, x, y);
            Draw.reset();
        }

        @Override
        public void updateTile() {
            if (!consValid()) {
                productionEfficiency = 0f;
                return;
            }

            heat = Mathf.lerpDelta(heat, generateTime >= 0.001f && enabled && consValid() ? 1f : 0f, 0.05f);

            Powder powder = null;
            for (Powder other : Vars.content.<Powder>getBy(ContentType.effect_UNUSED)) {
                if (powders.get(other) > 0.001f && other.flammability > minFlammability) {
                    powder = other;
                    break;
                }
            }

            if (powder != null) {
                float maximumPossible = maxPowderGenerate * delta();
                float used = Math.min(powders.get(powder) * delta(), maximumPossible);

                powders.remove(powder, used * power.graph.getUsageFraction());
                productionEfficiency = powder.flammability * used / maximumPossible;

                if (used > 0.001f && (generateTime -= delta()) <= 0f) {
                    if (Mathf.chance(0.1f)) generateEffect.at(x + Mathf.range(generateEffectRnd), y + Mathf.range(generateEffectRnd));
                    generateTime = 1f;
                }
            }
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public Bits filters() {
            return powderFilters;
        }

        @Override
        public float powderCapacity() {
            return powderCapacity;
        }
    }
}
