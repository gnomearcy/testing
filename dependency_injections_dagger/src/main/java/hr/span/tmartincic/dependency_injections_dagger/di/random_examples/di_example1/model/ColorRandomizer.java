package eu.span.dev.osijek.di.compdepend.model;

import android.graphics.Color;

import java.util.Random;

public class ColorRandomizer
{
    private int color;

    private final int[] COLORS = new int[]
    {
        Color.BLACK,
        Color.BLUE,
        Color.RED,
        Color.GREEN,
        Color.CYAN,
        Color.DKGRAY,
        Color.YELLOW
    };

    public ColorRandomizer()
    {
        int size = COLORS.length;
        Random r = new Random();
        int chosen = r.nextInt(size);
        color = COLORS[chosen];
    }
}
