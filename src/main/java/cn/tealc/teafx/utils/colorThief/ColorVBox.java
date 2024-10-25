package cn.tealc.teafx.utils.colorThief;

import javafx.scene.paint.Color;


/**
 * @program: TeaFX
 * @description:
 * @author: Leck
 * @create: 2024-08-01 18:57
 */
public class ColorVBox {
    private static final int SIGBITS = 5;
    private static final int RSHIFT = 8 - SIGBITS;
    private static final int MULT = 1 << RSHIFT;
    private final int[] histo;
    private int r1;
    private int r2;
    private int g1;
    private int g2;
    private int b1;
    private int b2;
    private int[] _avg;
    private Integer _volume;
    private Integer _count;

    public ColorVBox(int r1, int r2, int g1, int g2, int b1, int b2, int[] histo) {
        this.r1 = r1;
        this.r2 = r2;
        this.g1 = g1;
        this.g2 = g2;
        this.b1 = b1;
        this.b2 = b2;
        this.histo = histo;
    }


    public Color toColor() {
        int[] rgb = avg(true);
        return Color.rgb(rgb[0],rgb[1] ,rgb[2] );
    }


    @Override
    public String toString() {
        return "r1: " + r1 + " / r2: " + r2 + " / g1: " + g1 + " / g2: " + g2 + " / b1: " + b1
                + " / b2: " + b2;
    }


    public int volume(boolean force) {
        if (_volume == null || force) {
            _volume = ((r2 - r1 + 1) * (g2 - g1 + 1) * (b2 - b1 + 1));
        }
        return _volume;
    }

    public int count(boolean force) {
        if (_count == null || force) {
            int npix = 0;
            int i, j, k, index;
            for (i = r1; i <= r2; i++) {
                for (j = g1; j <= g2; j++) {
                    for (k = b1; k <= b2; k++) {
                        index = getColorIndex(i, j, k);
                        npix += histo[index];
                    }
                }
            }
            _count = npix;
        }
        return _count;
    }

    @Override
    public ColorVBox clone() {
        return new ColorVBox(r1, r2, g1, g2, b1, b2, histo);
    }

    public int[] avg(boolean force) {
        if (_avg == null || force) {
            int ntot = 0;
            int rsum = 0;
            int gsum = 0;
            int bsum = 0;
            int hval, i, j, k, histoindex;
            for (i = r1; i <= r2; i++) {
                for (j = g1; j <= g2; j++) {
                    for (k = b1; k <= b2; k++) {
                        histoindex = getColorIndex(i, j, k);
                        hval = histo[histoindex];
                        ntot += hval;
                        rsum += (hval * (i + 0.5) * MULT);
                        gsum += (hval * (j + 0.5) * MULT);
                        bsum += (hval * (k + 0.5) * MULT);
                    }
                }
            }
            if (ntot > 0) {
                _avg = new int[] {~~(rsum / ntot), ~~(gsum / ntot), ~~(bsum / ntot)};
            } else {
                _avg = new int[] {~~(MULT * (r1 + r2 + 1) / 2), ~~(MULT * (g1 + g2 + 1) / 2),
                        ~~(MULT * (b1 + b2 + 1) / 2)};
            }
        }
        return _avg;
    }

    public boolean contains(int[] pixel) {
        int rval = pixel[0] >> RSHIFT;
        int gval = pixel[1] >> RSHIFT;
        int bval = pixel[2] >> RSHIFT;
        return (rval >= r1 && rval <= r2 && gval >= g1 && gval <= g2 && bval >= b1
                && bval <= b2);
    }

    private int getColorIndex(int r, int g, int b) {
        return (r << (2 * SIGBITS)) + (g << SIGBITS) + b;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getG1() {
        return g1;
    }

    public void setG1(int g1) {
        this.g1 = g1;
    }

    public int getG2() {
        return g2;
    }

    public void setG2(int g2) {
        this.g2 = g2;
    }

    public int getB1() {
        return b1;
    }

    public void setB1(int b1) {
        this.b1 = b1;
    }

    public int getB2() {
        return b2;
    }

    public void setB2(int b2) {
        this.b2 = b2;
    }
}