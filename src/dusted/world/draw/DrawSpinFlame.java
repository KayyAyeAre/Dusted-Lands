package dusted.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.*;

public class DrawSpinFlame extends DrawBlock {
    public Color flameColor = Color.valueOf("ffb28f");
    public TextureRegion top;
    public float rotateSpeed = 0.6f;
    public float flameRadius = 7f, flameRadiusIn = 4f, flameCornerRadius = 4f, flameCornerRadiusIn = 2f;
    public float flameRadiusScl = 8f, flameRadiusMag = 3f, flameRadiusInMag = 1.6f;

    @Override
    public void draw(Building build) {
        if (build.warmup() > 0f) {
            float g = 0.3f;
            float r = 0.06f;
            float flameRad = flameRadius + Mathf.absin(flameRadiusScl, flameRadiusMag) * build.warmup();
            float flameRadIn = flameRadiusIn + Mathf.absin(flameRadiusScl, flameRadiusInMag) * build.warmup();
            float flameCornerRad = flameCornerRadius * build.warmup();
            float flameCornerRadIn = flameCornerRadiusIn * build.warmup();

            Draw.z(Layer.block + 0.01f);

            Draw.alpha(build.warmup());
            Draw.rect(top, build.x, build.y);

            for (int i = 0; i < 4; i++) {
                Draw.color(flameColor, (1f - g) + Mathf.absin(8f, g) + Mathf.random(r) - r);
                Fill.quad(build.x, build.y,
                        build.x + Angles.trnsx(build.totalProgress() * rotateSpeed + i * 90f + 45f, flameCornerRad), build.y + Angles.trnsy(build.totalProgress() * rotateSpeed + i * 90f + 45f, flameCornerRad),
                        build.x + Angles.trnsx(build.totalProgress() * rotateSpeed + i * 90f, flameRad), build.y + Angles.trnsy(build.totalProgress() * rotateSpeed + i * 90f, flameRad),
                        build.x + Angles.trnsx(build.totalProgress() * rotateSpeed + i * 90f - 45f, flameCornerRad), build.y + Angles.trnsy(build.totalProgress() * rotateSpeed + i * 90f - 45f, flameCornerRad)
                );
                Draw.color();
                Fill.quad(build.x, build.y,
                        build.x + Angles.trnsx(build.totalProgress() * rotateSpeed + i * 90f + 45f, flameCornerRadIn), build.y + Angles.trnsy(build.totalProgress() * rotateSpeed + i * 90f + 45f, flameCornerRadIn),
                        build.x + Angles.trnsx(build.totalProgress() * rotateSpeed + i * 90f, flameRadIn), build.y + Angles.trnsy(build.totalProgress() * rotateSpeed + i * 90f, flameRadIn),
                        build.x + Angles.trnsx(build.totalProgress() * rotateSpeed + i * 90f - 45f, flameCornerRadIn), build.y + Angles.trnsy(build.totalProgress() * rotateSpeed + i * 90f - 45f, flameCornerRadIn)
                );
            }
        }
    }

    @Override
    public void load(Block block) {
        top = Core.atlas.find(block + "-top");
    }
}
