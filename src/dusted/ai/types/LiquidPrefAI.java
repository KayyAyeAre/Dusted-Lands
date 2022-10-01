package dusted.ai.types;

import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.gen.*;
import mindustry.world.*;

import static mindustry.Vars.*;

//hover ai that prefers paths on liquid
public class LiquidPrefAI extends GroundAI {
    public static int costIndex;

    static {
        costIndex = Pathfinder.costTypes.add(
                (team, tile) ->
                        (((PathTile.team(tile) == team && !PathTile.teamPassable(tile)) || PathTile.team(tile) == 0) && PathTile.solid(tile)) ? -1 : 1 +
                                PathTile.health(tile) * 5 +
                                (PathTile.nearSolid(tile) ? 2 : 0) +
                                (PathTile.deep(tile) || PathTile.liquid(tile) ? 0 : 2)
        ).size - 1;
    }

    @Override
    public void pathfind(int pathTarget) {
        Tile tile = unit.tileOn();
        if (tile == null) return;
        Tile targetTile = pathfinder.getTargetTile(tile, pathfinder.getField(unit.team, costIndex, pathTarget));

        if (tile == targetTile) return;

        unit.movePref(vec.trns(unit.angleTo(targetTile.worldx(), targetTile.worldy()), unit.speed()));
    }
}
