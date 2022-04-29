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

public class PowderTurret extends Turret {
    public float powderCapacity = 20;
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public ObjectMap<Powder, BulletType> ammoTypes = new ObjectMap<>();
    public TextureRegion powderRegion, topRegion;

    public PowderTurret(String name) {
        super(name);
        outlinedIcon = 1;
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("ammo", entity -> {
            PowderTurretBuild build = (PowderTurretBuild) entity;
            return new Bar("stat.ammo", Pal.ammo, () -> build.powders.currentAmount() / build.powderCapacity());
        });
    }

    @Override
    public void setStats() {
        super.setStats();

        DustedStatValues.customStats(stats, cstats -> {
            cstats.addCStat("powder-capacity", StatValues.number(powderCapacity, StatUnit.none));
        });
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

    @Override
    public TextureRegion[] icons() {
        if (topRegion.found()) return new TextureRegion[]{baseRegion, region, topRegion};
        return super.icons();
    }

    public class PowderTurretBuild extends TurretBuild implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public boolean shouldActiveSound() {
            return wasShooting && enabled;
        }

        @Override
        public void draw() {
            super.draw();

            if (powderRegion.found()) {
                Draw.color(powders.current().color, powders.total() / powderCapacity);
                Draw.rect(powderRegion, x + tr2.x, y + tr2.y, rotation - 90);
                Draw.color();
            }
            if (topRegion.found()) Draw.rect(topRegion, x + tr2.x, y + tr2.y, rotation - 90);
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
