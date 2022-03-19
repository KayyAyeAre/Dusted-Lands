package dusted.world.meta.values;

import arc.scene.ui.layout.*;
import dusted.type.*;
import dusted.ui.*;
import mindustry.world.meta.*;

public class PowderValue implements StatValue {
    private final Powder powder;
    private final float amount;
    private final boolean perSecond;

    public PowderValue(Powder powder, float amount, boolean perSecond) {
        this.powder = powder;
        this.amount = amount;
        this.perSecond = perSecond;
    }

    @Override
    public void display(Table table) {
        table.add(new PowderDisplay(powder, amount, perSecond));
    }
}
