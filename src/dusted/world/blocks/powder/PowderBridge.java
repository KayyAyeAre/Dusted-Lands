package dusted.world.blocks.powder;

import arc.*;
import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.distribution.*;

public class PowderBridge extends ItemBridge {
    public int maxCharge = 16;
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;

    public PowderBridge(String name) {
        super(name);
        hasItems = false;
    }

    @Override
    public void setBars() {
        super.setBars();

        bars.add("powders", build -> {
            PowderBlockc entity = (PowderBlockc) build;
            Powder powder = entity.powderModule().current();
            return new Bar(() -> entity.powderModule().get(powder) <= 0.001f ? Core.bundle.get("bar.powder") : powder.localizedName, () -> powder.color, () -> entity.powderModule().get(powder) / powderCapacity);
        });

        bars.add("charge", build -> {
            Chargedc charged = (Chargedc) build;
            return new Bar(() -> Core.bundle.format("bar.charge"), () -> Pal.accent, () -> ((float) charged.charge()) / ((float) charged.maxCharge()));
        });
    }

    public class PowderBridgeBuild extends ItemBridgeBuild implements PowderBlockc, Chargedc {
        public PowderModule powders = new PowderModule();
        public int charge, properCharge;

        @Override
        public void updateTile() {
            time += cycleSpeed * delta();
            time2 += (cycleSpeed - 1f) * delta();

            checkIncoming();
            //TODO charge
            Building other = Vars.world.build(link);

            if (other == null || !linkValid(tile, other.tile())) {
                dumpPowder(powders.current(), 1f);
            } else {
                properCharge = 0;
                proximity.each(build -> {
                    if (build instanceof Chargedc entity && canCharge(build, this)) {
                        properCharge = Math.min(maxCharge, Math.max(properCharge, entity.charge() - 1));
                    }
                });

                charge = properCharge;

                if (charge > 0) {
                    ((Chargedc)other).charge(charge - 1);
                    ((ItemBridgeBuild) other).incoming.add(tile.pos());
                    if (consValid()) {
                        float alpha = 0.04f;
                        if (hasPower) {
                            alpha *= efficiency();
                        }
                        uptime = Mathf.lerpDelta(uptime, 1f, alpha);
                    } else {
                        uptime = Mathf.lerpDelta(uptime, 0f, 0.02f);
                    }

                    if (uptime >= 0.5f) {
                        if (movePowder(other, powders.current()) > 0.1f) {
                            cycleSpeed = Mathf.lerpDelta(cycleSpeed, 4f, 0.05f);
                        } else {
                            cycleSpeed = Mathf.lerpDelta(cycleSpeed, 1f, 0.01f);
                        }
                    }
                }
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return false;
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

        @Override
        public int charge() {
            return charge;
        }

        @Override
        public int maxCharge() {
            return maxCharge;
        }

        @Override
        public void charge(int charge) {
            this.charge = charge;
        }
    }
}
