package fi.tpt.minesweeper.core;

import java.util.function.Consumer;

/**
 * Created by timotapanainen on 24.11.14.
 */
public class Util {

    public static void iterateNeighbors(Coordinate c, int width, int height, Consumer<Coordinate> consumer) {
        int xlow = (c.x - 1 < 0 ? 0 : c.x - 1);
        int xhigh = (c.x + 1 >= width ? c.x : c.x + 1);
        int ylow = (c.y - 1 < 0 ? 0 : c.y - 1);
        int yhigh = (c.y + 1 >= height ? c.y : c.y + 1);

        for (int xi = xlow; xi <= xhigh; ++xi) {
            for (int yi = ylow; yi <= yhigh; ++yi) {
                if (xi == c.x && yi == c.y)
                    continue;
                consumer.accept(new Coordinate(xi, yi));
            }
        }
    }
}
