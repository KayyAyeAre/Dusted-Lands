package dusted.world.consumers;

import mindustry.world.consumers.*;

public abstract class ConsumePowderBase extends Consume {
    public float amount;

    public ConsumePowderBase(float amount) {
        this.amount = amount;
    }

    public ConsumePowderBase() {}
}
