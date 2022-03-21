package dusted.ui;

import arc.graphics.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import dusted.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

public class PowderDisplay extends Table {
    public final Powder powder;
    public final float amount;
    public final boolean perSecond;

    public PowderDisplay(Powder powder, float amount, boolean perSecond) {
        this.powder = powder;
        this.amount = amount;
        this.perSecond = perSecond;

        add(new Stack() {{
            add(new Image(powder.icon(Cicon.medium)));

            if (amount != 0) {
                Table t = new Table().left().bottom();
                t.add(Strings.autoFixed(amount, 2)).style(Styles.outlineLabel);
                add(t);
            }
        }}).size(8 * 4).padRight(3 + (amount != 0 && Strings.autoFixed(amount, 2).length() > 2 ? 8 : 0));

        if (perSecond) {
            add(StatUnit.perSecond.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
        }

        add(powder.localizedName);
    }
}
