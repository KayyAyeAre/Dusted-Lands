package dusted.world.interfaces;

import mindustry.gen.*;

//interface for blocks which require charge to interact with eachother
public interface Chargedc {
    int charge(Building accessor);
    int maxCharge();
    void setCharge(int charge);

    default boolean canCharge(Building from, Building to) {
        return !from.block().rotate || from.front() == to;
    }
}
