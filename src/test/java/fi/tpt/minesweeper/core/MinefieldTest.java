package fi.tpt.minesweeper.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by timotapanainen on 15.11.14.
 */
public class MinefieldTest {

    @Test
    public void testMinefield() throws Exception {
        Minefield mf = new Minefield(3, 3);
        String mfs =
                "...\n" +
                        "...\n" +
                        "...";
        assertEquals(mfs, mf.toString());
        assertEquals(3, mf.getWidth());
        assertEquals(3, mf.getHeight());
        assertEquals(0, mf.getMineCount());
        for (int x = 0; x < mf.getWidth(); ++x) {
            for (int y = 0; y < mf.getHeight(); ++y) {
                assertEquals(Minefield.EMPTY, mf.getCellValue(x, y));

            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinefieldNegativeWidth() throws Exception {
        new Minefield(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinefieldNegativeHeight() throws Exception {
        new Minefield(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinefieldSingleCell() throws Exception {
        new Minefield(1, 1);
    }

    @Test
    public void testPlantMine() throws Exception {
        Minefield mf = new Minefield(3, 3);
        mf.plantMine(2, 2);
        assertTrue(mf.isMineCell(2, 2));
        assertEquals(1, mf.getMineCount());
        String mfs =
                "...\n" +
                        ".11\n" +
                        ".1x";
        assertEquals(mfs, mf.toString());
        mf.plantMine(0, 0);
        assertEquals(2, mf.getMineCount());
        mfs =
                "x1.\n" +
                        "121\n" +
                        ".1x";
        assertEquals(mfs, mf.toString());
        mfs = "x21\n" +
                "2x2\n" +
                "12x";
        mf.plantMine(1, 1);
        assertEquals(mfs, mf.toString());

        mf = new Minefield(3, 3);
        mf.plantMine(0, 0);
        mf.plantMine(1, 0);
        mf.plantMine(2, 0);
        mf.plantMine(0, 1);
        mf.plantMine(2, 1);
        mf.plantMine(0, 2);
        mf.plantMine(1, 2);
        mf.plantMine(2, 2);
        mfs = "xxx\n" +
                "x8x\n" +
                "xxx";
        assertEquals(mfs, mf.toString());
    }

    @Test
    public void testPlantMinesRandomly() throws Exception {
        Minefield mf = new Minefield(3, 3);
        mf.plantMinesRandomly(3);
        assertEquals(3, mf.getMineCount());

        mf = new Minefield(3, 3);
        mf.plantMine(0, 0);
        mf.plantMinesRandomly(3);
        assertEquals(4, mf.getMineCount());

        mf = new Minefield(2, 2);
    }

    @Test
    public void testGetCellValue() throws Exception {
        Minefield mf = new Minefield(2, 2);
        mf.plantMine(0, 0);
        assertEquals(Minefield.MINE, mf.getCellValue(0, 0));
        assertEquals(1, mf.getCellValue(1, 0));

    }
}
