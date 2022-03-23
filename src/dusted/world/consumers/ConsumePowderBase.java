package dusted.world.consumers;

import arc.struct.*;
import dusted.world.interfaces.*;
import mindustry.world.consumers.*;

public abstract class ConsumePowderBase extends Consume implements CustomConsume {
    public final float amount;

    public ConsumePowderBase(float amount) {
        this.amount = amount;
    }

    public void addPowderFilters(Bits filter) {}

    @Override
    public Consume consume() {
        return this;
    }

    @Override
    public ConsumeType type() {
        return null;
    }

    protected float use(PowderBlockc entity) {
        return Math.min(amount * entity.build().edelta(), entity.powderCapacity());
    }
}
