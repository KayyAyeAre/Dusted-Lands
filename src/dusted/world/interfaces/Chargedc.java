package dusted.world.interfaces;

import mindustry.gen.*;

//interface for blocks which require charge to interact with eachother
public interface Chargedc extends BuildAccessor {
    int charge(Building accessor);
    int maxCharge();
    void setCharge(int charge);

    default void updateCharge() {
        int[] properCharge = {0};
        build().proximity.each(build -> {
            if (build instanceof Chargedc entity && canCharge(build, build())) {
                properCharge[0] = Math.min(maxCharge(), Math.max(properCharge[0], entity.charge(build()) - 1));
            }
        });

        if (build().consValid()) properCharge[0] = maxCharge();

        setCharge(properCharge[0]);
    }

    default boolean canCharge(Building from, Building to) {
        return (!from.block().rotate && to.block().rotate && to.front() != from) || (from.block().rotate && from.front() == to);
    }
}
