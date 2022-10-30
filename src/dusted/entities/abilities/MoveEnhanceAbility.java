package dusted.entities.abilities;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.graphics.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class MoveEnhanceAbility extends Ability {
    public float minSpeed, maxSpeed;
    public float reloadMultiplier = 0.6f;
    public float stroke = 2f, sectorFrac = 0.3f, sectorRadius = 12f, rotateSpeed = 1f;
    public int sectors = 3;
    public Color color = DustedPal.decayingYellow;

    public MoveEnhanceAbility(float minSpeed, float maxSpeed) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;

        display = false;
    }

    @Override
    public void update(Unit unit) {
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));
        unit.reloadMultiplier *= 1f + scl * reloadMultiplier;
    }

    @Override
    public void draw(Unit unit) {
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));

        Draw.z(Layer.effect);
        Draw.color(color);
        Lines.stroke(scl * stroke);
        for (int i = 0; i < sectors; i++) {
            Lines.arc(unit.x, unit.y, sectorRadius, sectorFrac, (360f / sectors) * i + Time.time * rotateSpeed);
        }
        Draw.reset();
    }
}
