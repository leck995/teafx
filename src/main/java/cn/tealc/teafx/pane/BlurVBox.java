package cn.tealc.teafx.pane;

import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @program: TeaFX
 * @description:
 * @author: Leck
 * @create: 2024-08-06 14:56
 */
public class BlurVBox extends StackPane {
    private final VBox vbox=new VBox();
    private static final GaussianBlur blur = new GaussianBlur(30);
    public BlurVBox() {
        super();
        Region region = new Region();

        region.prefWidthProperty().bind(widthProperty());
        region.prefHeightProperty().bind(heightProperty());
        region.setEffect(blur);
        getChildren().addAll(region,vbox);
    }

    public BlurVBox(Node... nodes) {
        super(nodes);
        Region region = new Region();
        region.prefWidthProperty().bind(widthProperty());
        region.prefHeightProperty().bind(heightProperty());
        region.setEffect(blur);
        getChildren().addAll(region,vbox);
    }



}