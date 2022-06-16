package dusted.world.blocks.defense.turrets;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;

public class PowderTurret extends Turret implements PowderBlockc {
    public float powderCapacity = 20;
    public boolean[] powderFilter = {};
    public ObjectMap<Powder, BulletType> ammoTypes = new ObjectMap<>();
    public TextureRegion powderRegion, topRegion;

    public PowderTurret(String name) {
        super(name);
        outlinedIcon = 1;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("ammo", entity -> {
            PowderTurretBuild build = (PowderTurretBuild) entity;
            return new Bar("stat.ammo", Pal.ammo, () -> build.powders.currentAmount() / powderCapacity);
        });
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(DustedStats.powderCapacity, powderCapacity, DustedStatUnits.powderUnits);
        stats.add(Stat.ammo, StatValues.ammo(ammoTypes));
    }

    public void ammo(Object... objects) {
        ammoTypes = ObjectMap.of(objects);
    }

    @Override
    public void load() {
        super.load();
        powderRegion = Core.atlas.find(name + "-powder");
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void init() {
        powderFilter = new boolean[Vars.content.getBy(ContentType.effect_UNUSED).size];
        consume(new ConsumePowderFilter(i -> ammoTypes.containsKey(i), 1f) {
            @Override
            public void update(Building entity) {}

            @Override
            public void display(Stats stats) {}
        });

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

    public class PowderTurretBuild extends TurretBuild implements PowderBuildc {
        public PowderModule powders = new PowderModule();

        @Override
        public boolean shouldActiveSound() {
            return wasShooting && enabled;
        }

        @Override
        public void updateTile() {
            unit.ammo(unit.type().ammoCapacity * powders.currentAmount() / powderCapacity);

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
        public void write(Writes write) {
            super.write(write);
            powders.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            powders.read(read);
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public Building build() {
            return this;
        }
    }
}
