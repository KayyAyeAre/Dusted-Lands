package dusted.world.consumers;

import mindustry.gen.*;

public class ConsumePowderFlammable extends ConsumePowderFilter {
    public float minFlammability;

    public ConsumePowderFlammable(float minFlammability, float amount) {
        this.amount = amount;
        this.minFlammability = minFlammability;
        filter = powder -> powder.flammability > this.minFlammability;
    }

    @Override
    public float efficiencyMultiplier(Building build) {
        var liq = getConsumed(build);
        return liq == null ? 0f : liq.flammability;
    }
}
