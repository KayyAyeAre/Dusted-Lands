package dusted.input;

import arc.*;
import mindustry.*;

public class DustedInputHandler implements ApplicationListener {
    public CustomInput input;

    {
        input = Vars.mobile ? new DustedMobileInput() : new DustedDesktopInput();
    }
    @Override
    public void update() {
        input.update();
    }
}
