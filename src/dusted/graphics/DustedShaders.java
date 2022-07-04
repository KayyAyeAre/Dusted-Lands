package dusted.graphics;

import arc.*;
import arc.graphics.gl.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;

public class DustedShaders {
    public static DecayShieldShader decayShield;

    public static void init() {
        if (!Vars.headless) {
            decayShield = new DecayShieldShader();
        }
    }

    public static class DecayShieldShader extends ModShader {
        public DecayShieldShader() {
            super("decayshield", "screenspace");
        }

        @Override
        public void apply() {
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_offset",
                    Core.camera.position.x - Core.camera.width / 2,
                    Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_texsize", Core.camera.width, Core.camera.height);
            setUniformf("u_invsize", 1f / Core.camera.width, 1f / Core.camera.height);
        }
    }

    public static class ModShader extends Shader {
        public ModShader(String frag, String vert) {
            super(Vars.tree.get("shaders/" + vert + ".vert"), Vars.tree.get("shaders/" + frag + ".frag"));
        }
    }
}
