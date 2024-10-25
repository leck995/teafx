package cn.tealc.teafx.pane;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * @program: TeaFX
 * @description:
 * @author: Leck
 * @create: 2024-08-06 14:53
 */
public class BlurStackPane extends StackPane {
    private static final GaussianBlur blur = new GaussianBlur(20);
    public BlurStackPane() {
        super();
        Region region = new Region();
        region.setStyle("-fx-background-color: #fffFFF20");
        region.prefWidthProperty().bind(widthProperty());
        region.prefHeightProperty().bind(heightProperty());
        region.setEffect(blur);
        getChildren().add(region);
    }

    public BlurStackPane(Node... nodes) {
        super(nodes);
        Region region = new Region();

        region.prefWidthProperty().bind(widthProperty());
        region.prefHeightProperty().bind(heightProperty());
        region.setEffect(blur);
        getChildren().add(region);
    }


}