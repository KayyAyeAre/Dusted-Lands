package dusted.world.consumers;

import arc.struct.*;
import arc.util.*;
import dusted.world.interfaces.*;
import mindustry.world.consumers.*;

import java.util.*;

public abstract class ConsumePowderBase extends Consume {
    public final float amount;

    public ConsumePowderBase(float amount) {
        this.amount = amount;
    }

    public void add(Consumers consumers) {
        Consume[] map = Arrays.copyOf(Reflect.<Consume[]>get(consumers, "map"), 4);
        map[3] = this;
        Reflect.set(consumers, "map", map);
    }

    public void addPowderFilters(Bits filter) {}

    @Override
    public ConsumeType type() {
        return null;
    }

    protected float use(PowderBlockc entity) {
        return Math.min(amount * entity.build().edelta(), entity.powderCapacity());
    }
}
