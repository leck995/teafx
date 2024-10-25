import cn.tealc.teafx.utils.colorThief.ColorMap;
import cn.tealc.teafx.utils.colorThief.ColorThief;
import cn.tealc.teafx.utils.colorThief.ColorVBox;
import cn.tealc.teafx.utils.colorThief.MMCQ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @program: TeaFX
 * @description:
 * @author: Leck
 * @create: 2024-08-01 18:35
 */
public class ColorThiefTest {
    public static void main(String[] args) throws IOException {
        File file=new File("C:\\Leck\\Code\\Reposity\\FX\\TeaFX\\src\\test\\resources\\image\\photo1.jpg");
        BufferedImage image= ImageIO.read(file);
        ColorMap colorMap = ColorThief.getColorMap(image, 5);
        colorMap.getColors().forEach(System.out::println);
    }
}