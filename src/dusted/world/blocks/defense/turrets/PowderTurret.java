package dusted.world.blocks.defense.turrets;

import arc.struct.*;
import dusted.type.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import mindustry.world.meta.values.*;

public class PowderTurret extends Turret {
    public float powderCapacity = 20;
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public ObjectMap<Powder, BulletType> ammoTypes = new ObjectMap<>();

    public PowderTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.ammo, new AmmoListValue<>(ammoTypes));
    }

    public void ammo(Object... objects) {
        ammoTypes = ObjectMap.of(objects);
    }

    @Override
    public void init() {
        new ConsumePowderFilter(i -> ammoTypes.containsKey(i), 1f) {
            @Override
            public boolean valid(Building entity) {
                return entity instanceof PowderBlockc build && build.powderModule().total() > 0.001f;
            }

            @Override
            public void update(Building entity) {}

            @Override
            public void display(Stats stats) {}
        }.add(consumes);

        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });

        super.init();
    }

    public class PowderTurretBuild extends TurretBuild implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public void updateTile() {
            unit.ammo(unit.type().ammoCapacity * liquids.currentAmount() / liquidCapacity);

            super.updateTile();
        }

        @Override
        public BulletType useAmmo() {
            if (cheating()) return ammoTypes.get(powders.current());
            BulletType type = ammoTypes.get(powders.current());
            powders.remove(powders.current(), 1f / type.ammoMultiplier);
            return type;
        }

        @Override
        public BulletType peekAmmo() {
            return ammoTypes.get(powders.current());
        }

        @Override
        public boolean hasAmmo() {
            return ammoTypes.get(powders.current()) != null && powders.total() >= 1f / ammoTypes.get(powders.current()).ammoMultiplier;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return false;
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return ammoTypes.get(powder) != null
                    && (powders.current() == powder || (ammoTypes.containsKey(powder)
                    && (!ammoTypes.containsKey(powders.current()) || powders.currentAmount() <= 1f / ammoTypes.get(powders.current()).ammoMultiplier + 0.001f)));
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
        public float powderCapacity() {
            return powderCapacity;
        }

        @Override
        public Bits filters() {
            return powderFilters;
        }
    }
}
