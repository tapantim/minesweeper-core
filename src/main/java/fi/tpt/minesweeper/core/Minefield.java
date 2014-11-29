package fi.tpt.minesweeper.core;

import java.util.Random;

/**
 * The Minefield class is a grid of cells having
 */
public class Minefield {

    public static final int MINE = -1;
    public static final int EMPTY = 0;

    private int[][] grid;
    private int width;
    private int height;
    private Random random;
    private int mineCount = 0;

    /**
     * Creates minefield with specified width and height.
     *
     * @param width  width of the field, must be > 0
     * @param height height of the field, must be > 0
     * @throws java.lang.IllegalArgumentException if width <= 0 or height <= 0 or width * height = 1
     */
    public Minefield(int width, int height) {
        if (width <= 0)
            throw new IllegalArgumentException("width must be > 0");
        if (height <= 0)
            throw new IllegalArgumentException("height must be > 0");
        if (width * height == 1)
            throw new IllegalArgumentException("minefield must contain more than 1 cell");
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
        this.random = new Random();
    }

    /**
     * Plants mine to cell (x,y) and increments numbers in neighboring cells that do
     * not contain a mine.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @throws java.lang.IllegalArgumentException if coordinate is out of bounds
     */
    public void plantMine(int x, int y) {
        if (isMineCell(x, y))
            throw new IllegalArgumentException("cell already contains a mine");
        setCellValue(x, y, MINE);
        Util.iterateNeighbors(
                new Coordinate(x, y), width, height,
                (neighbor) -> {
                    if (!isMineCell(neighbor)) { // do not update cell that contains mine
                        int content = getCellValueInternal(neighbor);
                        setCellValue(neighbor, content + 1);
                    }
                }
        );
    }


    /**
     * Plant specified number of mines to this field.
     *
     * @param requestedMineCount mines to plant
     */
    public void plantMinesRandomly(int requestedMineCount) {
        if (requestedMineCount + this.mineCount >= width * height)
            throw new IllegalArgumentException("cannot plant a mine to every cell");
        int minesToPlant = requestedMineCount;
        int x, y;
        while (minesToPlant > 0) {
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (isMineCell(x, y));
            plantMine(x, y);
            --minesToPlant;
        }
    }

    /**
     * Returns true if the specified coordinate contains a mine, false otherwise.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if given coordinates contains a mine
     * @throws java.lang.IllegalArgumentException if coordinate is out of bounds
     */
    public boolean isMineCell(int x, int y) {
        return getCellValue(x, y) == MINE;
    }

    /**
     * Returns true if the specified coordinate contains a mine, false otherwise.
     *
     * @param c coordinate
     * @return true if the given coordinate contains a mine
     * @throws java.lang.IllegalArgumentException if coordinate is out of bounds
     */
    public boolean isMineCell(Coordinate c) {
        return isMineCell(c.x, c.y);
    }

    /**
     * Returns true if the specified cell is empty, false otherwise.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if given specified cell is empty
     * @throws java.lang.IllegalArgumentException if coordinate is out of bounds
     */
    public boolean isEmptyCell(int x, int y) {
        return getCellValue(x, y) == EMPTY;
    }

    /**
     * Return value in the specified cell.
     *
     * @param x coordinate x
     * @param y coordinate y
     * @return value in the cell
     */
    public int getCellValue(int x, int y) {
        checkCoordinate(x, y);
        return grid[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellCount() {
        return width * height;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void checkCoordinate(int x, int y) {
        if (x < 0 || x >= width)
            throw new IllegalArgumentException("x must be within range [0, " + (width - 1) + "]");
        if (y < 0 || y >= height)
            throw new IllegalArgumentException("y must be within range [0, " + (height - 1) + "]");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; ++x) {
                int cellContent = getCellValueInternal(x, y);
                if (cellContent == MINE)
                    sb.append("x");
                else if (cellContent == EMPTY)
                    sb.append(".");
                else
                    sb.append("" + cellContent);
            }
            if (y < height - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    private int getCellValueInternal(Coordinate c) {
        return grid[c.x][c.y];
    }

    private int getCellValueInternal(int x, int y) {
        return grid[x][y];
    }


    private void setCellValue(int x, int y, int value) {
        grid[x][y] = value;
        if (value == MINE)
            ++mineCount;
    }

    private void setCellValue(Coordinate c, int value) {
        setCellValue(c.x, c.y, value);
    }

}
