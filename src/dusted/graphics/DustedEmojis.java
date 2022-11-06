package dusted.graphics;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.Font.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import dusted.content.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.mod.Mods.*;
import mindustry.ui.*;
import mindustry.world.blocks.*;

public class DustedEmojis {
    public static int id;

    public static void load() {
        //load the character id
        LoadedMod mod = Vars.mods.getMod("dusted-lands");
        OrderedMap<String, String> map = new OrderedMap<>();
        PropertiesUtils.load(map, Core.files.internal("icons/icons.properties").reader(256));

        id = 0xF8FF;

        ObjectMap<String, String> content2id = new ObjectMap<>();
        map.each((key, val) -> content2id.put(val.split("\\|")[0], key));

        Seq<UnlockableContent> cont = Seq.withArrays(
                Vars.content.blocks(),
                Vars.content.items(),
                Vars.content.liquids(),
                Vars.content.units(),
                Vars.content.statusEffects()
        );
        cont.removeAll(u -> u instanceof ConstructBlock || u == Blocks.air || u.minfo.mod == mod);

        for (UnlockableContent ignored : cont) {
            id--;
        }

        //actually start loading the emojis
        Seq<Font> fonts = Seq.with(Fonts.def, Fonts.outline);

        Texture pure = DustedItems.zircon.uiIcon.texture;
        Texture genned = DustedBlocks.abrade.uiIcon.texture;

        fonts.each(f -> f.getRegions().add(new TextureRegion(pure)));
        fonts.each(f -> f.getRegions().add(new TextureRegion(genned)));

        int purePage = Fonts.def.getRegions().indexOf(t -> t.texture == pure);
        int gennedPage = Fonts.def.getRegions().indexOf(t -> t.texture == genned);

        Seq.<UnlockableContent>withArrays(
                Vars.content.blocks(),
                Vars.content.items(),
                Vars.content.liquids(),
                Vars.content.units(),
                Vars.content.statusEffects()
        ).removeAll(u -> u.minfo.mod != mod).each(c -> {
            if (c.minfo.mod == mod) {
                TextureRegion region = c.uiIcon;
                id--;

                Reflect.<ObjectIntMap<String>>get(Fonts.class, "unicodeIcons").put(c.name, id);
                Reflect.<ObjectMap<String, String>>get(Fonts.class, "stringIcons").put(c.name, ((char) id) + "");

                int size = (int) (Fonts.def.getData().lineHeight / Fonts.def.getData().scaleY);

                Vec2 out = Scaling.fit.apply(region.width, region.height, size, size);

                Glyph glyph = new Glyph();
                glyph.id = id;
                glyph.srcX = 0;
                glyph.srcY = 0;
                glyph.width = (int) out.x;
                glyph.height = (int) out.y;
                glyph.u = region.u;
                glyph.v = region.v2;
                glyph.u2 = region.u2;
                glyph.v2 = region.v;
                glyph.xoffset = 0;
                glyph.yoffset = -size;
                glyph.xadvance = size;
                glyph.kerning = null;
                glyph.fixedWidth = true;
                glyph.page = region.texture == pure ? purePage : gennedPage;
                fonts.each(f -> f.getData().setGlyph(id, glyph));
            }
        });
    }
}
