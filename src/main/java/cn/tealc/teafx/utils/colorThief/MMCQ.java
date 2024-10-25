/*
 * Java Color Thief
 * by Sven Woltmann, Fonpit AG
 *
 * https://www.androidpit.com
 * https://www.androidpit.de
 *
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 *
 * Thanks
 * ------
 * Lokesh Dhakar - for the original Color Thief JavaScript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 */

package cn.tealc.teafx.utils.colorThief;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MMCQ {
    private static final int SIGBITS = 5;
    private static final int RSHIFT = 8 - SIGBITS;
    private static final int MULT = 1 << RSHIFT;
    private static final int HISTOSIZE = 1 << (3 * SIGBITS);
    private static final int VBOX_LENGTH = 1 << SIGBITS;
    private static final double FRACT_BY_POPULATION = 0.75;
    private static final int MAX_ITERATIONS = 1000;

    private static final Comparator<ColorVBox> COMPARATOR_COUNT = new Comparator<ColorVBox>() {
        @Override
        public int compare(ColorVBox a, ColorVBox b) {
            return a.count(false) - b.count(false);
        }
    };
    private static final Comparator<ColorVBox> COMPARATOR_PRODUCT = new Comparator<ColorVBox>() {
        @Override
        public int compare(ColorVBox a, ColorVBox b) {
            int aCount = a.count(false);
            int bCount = b.count(false);
            int aVolume = a.volume(false);
            int bVolume = b.volume(false);

            // If count is 0 for both (or the same), sort by volume
            if (aCount == bCount) {
                return aVolume - bVolume;
            }

            // Otherwise sort by products
            return Long.compare((long) aCount * aVolume, (long) bCount * bVolume);
        }
    };

    /**
     * Get reduced-space color index for a pixel.
     *
     * @param r
     *            the red value
     * @param g
     *            the green value
     * @param b
     *            the blue value
     *
     * @return the color index
     */
    static int getColorIndex(int r, int g, int b) {
        return (r << (2 * SIGBITS)) + (g << SIGBITS) + b;
    }

    /**
     * Histo (1-d array, giving the number of pixels in each quantized region of color space), or
     * null on error.
     */
    private static int[] getHisto(int[][] pixels) {
        int[] histo = new int[HISTOSIZE];
        int index, rval, gval, bval;

        int numPixels = pixels.length;
        for (int i = 0; i < numPixels; i++) {
            int[] pixel = pixels[i];
            rval = pixel[0] >> RSHIFT;
            gval = pixel[1] >> RSHIFT;
            bval = pixel[2] >> RSHIFT;
            index = getColorIndex(rval, gval, bval);
            histo[index]++;
        }
        return histo;
    }

    private static ColorVBox vboxFromPixels(int[][] pixels, int[] histo) {
        int rmin = 1000000, rmax = 0;
        int gmin = 1000000, gmax = 0;
        int bmin = 1000000, bmax = 0;

        int rval, gval, bval;

        // find min/max
        int numPixels = pixels.length;
        for (int i = 0; i < numPixels; i++) {
            int[] pixel = pixels[i];
            rval = pixel[0] >> RSHIFT;
            gval = pixel[1] >> RSHIFT;
            bval = pixel[2] >> RSHIFT;

            if (rval < rmin) {
                rmin = rval;
            } else if (rval > rmax) {
                rmax = rval;
            }

            if (gval < gmin) {
                gmin = gval;
            } else if (gval > gmax) {
                gmax = gval;
            }

            if (bval < bmin) {
                bmin = bval;
            } else if (bval > bmax) {
                bmax = bval;
            }
        }

        return new ColorVBox(rmin, rmax, gmin, gmax, bmin, bmax, histo);
    }

    private static ColorVBox[] medianCutApply(int[] histo, ColorVBox vbox) {
        if (vbox.count(false) == 0) {
            return null;
        }

        // only one pixel, no split
        if (vbox.count(false) == 1) {
            return new ColorVBox[] {vbox.clone(), null};
        }

        int rw = vbox.getR2() - vbox.getR1() + 1;
        int gw = vbox.getG2() - vbox.getG1() + 1;
        int bw = vbox.getB2() - vbox.getB1() + 1;
        int maxw = Math.max(Math.max(rw, gw), bw);

        // Find the partial sum arrays along the selected axis.
        int total = 0;
        int[] partialsum = new int[VBOX_LENGTH];
        Arrays.fill(partialsum, -1); // -1 = not set / 0 = 0
        int[] lookaheadsum = new int[VBOX_LENGTH];
        Arrays.fill(lookaheadsum, -1); // -1 = not set / 0 = 0
        int i, j, k, sum, index;

        if (maxw == rw) {
            for (i = vbox.getR1(); i <= vbox.getR2(); i++) {
                sum = 0;
                for (j = vbox.getG1(); j <= vbox.getG2(); j++) {
                    for (k = vbox.getB1(); k <= vbox.getB2(); k++) {
                        index = getColorIndex(i, j, k);
                        sum += histo[index];
                    }
                }
                total += sum;
                partialsum[i] = total;
            }
        } else if (maxw == gw) {
            for (i = vbox.getG1(); i <= vbox.getG2(); i++) {
                sum = 0;
                for (j = vbox.getR1(); j <= vbox.getR2(); j++) {
                    for (k = vbox.getB1(); k <= vbox.getB2(); k++) {
                        index = getColorIndex(j, i, k);
                        sum += histo[index];
                    }
                }
                total += sum;
                partialsum[i] = total;
            }
        } else
            /* maxw == bw */
        {
            for (i = vbox.getB1(); i <= vbox.getB2(); i++) {
                sum = 0;
                for (j = vbox.getR1(); j <= vbox.getR2(); j++) {
                    for (k = vbox.getG1(); k <= vbox.getG2(); k++) {
                        index = getColorIndex(j, k, i);
                        sum += histo[index];
                    }
                }
                total += sum;
                partialsum[i] = total;
            }
        }

        for (i = 0; i < VBOX_LENGTH; i++) {
            if (partialsum[i] != -1) {
                lookaheadsum[i] = total - partialsum[i];
            }
        }

        // determine the cut planes
        return maxw == rw ? doCut('r', vbox, partialsum, lookaheadsum, total)
                : maxw == gw ? doCut('g', vbox, partialsum, lookaheadsum, total)
                : doCut('b', vbox, partialsum, lookaheadsum, total);
    }

    private static ColorVBox[] doCut(
            char color,
            ColorVBox vbox,
            int[] partialsum,
            int[] lookaheadsum,
            int total) {
        int vbox_dim1;
        int vbox_dim2;

        if (color == 'r') {
            vbox_dim1 = vbox.getR1();
            vbox_dim2 = vbox.getR2();
        } else if (color == 'g') {
            vbox_dim1 = vbox.getG1();
            vbox_dim2 = vbox.getG2();
        } else
            /* color == 'b' */
        {
            vbox_dim1 = vbox.getB1();
            vbox_dim2 = vbox.getB2();
        }

        int left, right;
        ColorVBox vbox1 = null, vbox2 = null;
        int d2, count2;

        for (int i = vbox_dim1; i <= vbox_dim2; i++) {
            if (partialsum[i] > total / 2) {
                vbox1 = vbox.clone();
                vbox2 = vbox.clone();

                left = i - vbox_dim1;
                right = vbox_dim2 - i;

                if (left <= right) {
                    d2 = Math.min(vbox_dim2 - 1, ~~(i + right / 2));
                } else {
                    // 2.0 and cast to int is necessary to have the same behaviour as in JavaScript
                    d2 = Math.max(vbox_dim1, ~~((int) (i - 1 - left / 2.0)));
                }

                // avoid 0-count boxes
                while (d2 < 0 || partialsum[d2] <= 0) {
                    d2++;
                }
                count2 = lookaheadsum[d2];
                while (count2 == 0 && d2 > 0 && partialsum[d2 - 1] > 0) {
                    count2 = lookaheadsum[--d2];
                }

                // set dimensions
                if (color == 'r') {
                    vbox1.setR2(d2);
                    vbox2.setR1(d2 + 1);
                } else if (color == 'g') {
                    vbox1.setG2(d2);
                    vbox2.setG1(d2 + 1);
                } else {
                    vbox1.setB2(d2);
                    vbox2.setB1(d2 + 1);
                }

                return new ColorVBox[] {vbox1, vbox2};
            }
        }

        throw new RuntimeException("VBox can't be cut");
    }

    public static ColorMap quantize(int[][] pixels, int maxcolors) {
        // short-circuit
        if (pixels.length == 0 || maxcolors < 2 || maxcolors > 256) {
            return null;
        }

        int[] histo = getHisto(pixels);

        // get the beginning vbox from the colors
        ColorVBox vbox = vboxFromPixels(pixels, histo);
        ArrayList<ColorVBox> pq = new ArrayList<>();
        pq.add(vbox);

        // Round up to have the same behaviour as in JavaScript
        int target = (int) Math.ceil(FRACT_BY_POPULATION * maxcolors);

        // first set of colors, sorted by population
        iter(pq, COMPARATOR_COUNT, target, histo);

        // Re-sort by the product of pixel occupancy times the size in color space.
        Collections.sort(pq, COMPARATOR_PRODUCT);

        // next set - generate the median cuts using the (npix * vol) sorting.
        if (maxcolors > pq.size()) {
            iter(pq, COMPARATOR_PRODUCT, maxcolors, histo);
        }

        // Reverse to put the highest elements first into the color map
        Collections.reverse(pq);

        // calculate the actual colors
        ColorMap cmap = new ColorMap();
        for (ColorVBox vb : pq) {
            cmap.push(vb);
        }

        return cmap;
    }

    /**
     * Inner function to do the iteration.
     */
    private static void iter(List<ColorVBox> lh, Comparator<ColorVBox> comparator, int target, int[] histo) {
        int niters = 0;
        ColorVBox vbox;

        while (niters < MAX_ITERATIONS) {
            vbox = lh.get(lh.size() - 1);
            if (vbox.count(false) == 0) {
                Collections.sort(lh, comparator);
                niters++;
                continue;
            }
            lh.remove(lh.size() - 1);

            // do the cut
            ColorVBox[] vboxes = medianCutApply(histo, vbox);
            ColorVBox vbox1 = vboxes[0];
            ColorVBox vbox2 = vboxes[1];

            if (vbox1 == null) {
                throw new RuntimeException("vbox1 not defined; shouldn't happen!");
            }

            lh.add(vbox1);
            if (vbox2 != null) {
                lh.add(vbox2);
            }
            Collections.sort(lh, comparator);

            if (lh.size() >= target) {
                return;
            }
            if (niters++ > MAX_ITERATIONS) {
                return;
            }
        }
    }

}
