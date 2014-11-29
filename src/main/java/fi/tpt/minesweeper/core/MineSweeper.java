package fi.tpt.minesweeper.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timotapanainen on 9.11.14.
 */
public class MineSweeper {

    private Set<Coordinate> openedCells;
    private Minefield field;
    private StopWatch stopWatch;
    private boolean gameEndedMineExplodedState = false;
    private boolean gameEndedFieldClearedState = false;
    private boolean gameRunningState = false;


    /**
     * Creates a minefield with specified width, height and mine count.
     *
     * @param width     width of minefield, must be > 0
     * @param height    height of minefield, must be > 0
     * @param mineCount mine count, must be > 0 and < width * height
     * @throws java.lang.IllegalArgumentException if any of the following is true:
     *                                            <ul>
     *                                            <li>width &lt; 1</li>
     *                                            <li>height &lt; 1</li>
     *                                            <li>width * height = 1</li>
     *                                            <li>mineCount &lt; 1 or &gt;= width * height</li>
     *                                            </ul>
     */
    public MineSweeper(int width, int height, int mineCount) {
        this.field = new Minefield(width, height);
        this.field.plantMinesRandomly(mineCount);
        this.openedCells = new HashSet<>();
        this.stopWatch = new StopWatch();
    }

    public MineSweeper(Minefield minefield) {
        if (minefield == null)
            throw new IllegalArgumentException("minefield cannot be null");
        this.field = minefield;
        this.openedCells = new HashSet<>();
        this.stopWatch = new StopWatch();
    }

    /**
     * Opens specified minefield cell and returns
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return tiles that were opened
     */
    public Set<Coordinate> openCell(int x, int y) {
        if (isGameEnded())
            throw new IllegalStateException("game has ended");
        if (isCellOpened(x, y))
            throw new IllegalStateException("cell is already opened");

        if (!isGameRunning()) {
            gameRunningState = true;
            stopWatch.start();
        }

        Set<Coordinate> newOpenedCells = openCells(x, y);

        if (field.isMineCell(x, y)) {
            gameRunningState = false;
            gameEndedMineExplodedState = true;
            stopWatch.stop();
        } else if (openedCells.size() + field.getMineCount() == field.getCellCount()) {
            gameRunningState = false;
            gameEndedFieldClearedState = true;
            stopWatch.stop();
        }
        return newOpenedCells;
    }

    public boolean isGameEnded() {
        return isGameEndedSuccessfully() || isGameEndedExplosion();
    }

    public boolean isGameEndedSuccessfully() {
        return gameEndedFieldClearedState;
    }

    public boolean isGameEndedExplosion() {
        return gameEndedMineExplodedState;
    }

    public boolean isGameRunning() {
        return gameRunningState;
    }

    public boolean isCellOpened(int x, int y) {
        return openedCells.contains(new Coordinate(x, y));
    }

    private Set<Coordinate> openCells(int x, int y) {
        Set<Coordinate> newOpenedCellSet = new HashSet<>();
        openCells(new Coordinate(x, y), newOpenedCellSet);
        return newOpenedCellSet;
    }

    /**
     * Opens minefield cells starting from the specified cell and collects
     * all opened cells into newOpenedSet. Method works as follows:
     * <ol>
     * <li>does nothing if specified cell is already opened</li>
     * <li>opens specified cell and adds it to newOpenedSet if the cell contains a mine
     * or is next to mine</li>
     * <li>opens specified empty cell, all neighboring empty cells recursively and their
     * neighbors if not already opened. </li>
     * </ol>
     *
     * @param cell         cell to open
     * @param newOpenedSet set of cells opened during method execution
     */
    private void openCells(Coordinate cell, Set<Coordinate> newOpenedSet) {
        if (openedCells.contains(cell) || newOpenedSet.contains(cell))
            return;
        newOpenedSet.add(cell);
        openedCells.add(cell);
        if (field.isEmptyCell(cell.x, cell.y)) {
            for (Coordinate neighbor : getNeighbors(cell)) {
                openCells(neighbor, newOpenedSet);
            }
        }
    }

    private Collection<Coordinate> getNeighbors(Coordinate c) {
        Collection<Coordinate> neighbors = new ArrayList<>();
        Util.iterateNeighbors(c, field.getWidth(), field.getHeight(),
                neighbors::add);
        return neighbors;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); ++x) {
                if (!isCellOpened(x, y)) {
                    sb.append("#");
                } else {
                    if (field.isMineCell(x, y))
                        sb.append("x");
                    else if (field.isEmptyCell(x, y))
                        sb.append(".");
                    else
                        sb.append("" + field.getCellValue(x, y));
                }
            }
            if (y < field.getHeight() - 1)
                sb.append("\n");
        }
        return sb.toString();
    }


    public long getTime() {
        return stopWatch.getTime();
    }
}
