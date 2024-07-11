package cn.tealc.teafx.dialog;


import cn.tealc.teafx.stage.TeaStage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @program: JavaFXTitleBarDemo
 * @description:
 * @author: leck
 * @create: 2021-08-23 18:20
 */
public class DragDialogHandler implements EventHandler<MouseEvent> {
    private Dialog<ButtonType> primaryStage;
    private boolean autoScreen;

    private SimpleObjectProperty<Pos> position;

    private Stage hideStage;
    private Stage popup;


    private double oldStageX;
    private double oldStageY;
    private double oldScreenX;
    private double oldScreenY;

    public DragDialogHandler(Dialog<ButtonType> primaryStage, boolean autoScreen) { //构造器
        this.primaryStage = primaryStage;
        this.autoScreen = autoScreen;
    }

    @Override
    public void handle(MouseEvent e) {
        Screen screen =Screen.getScreensForRectangle(primaryStage.getX(),primaryStage.getY(),primaryStage.getWidth(), primaryStage.getHeight()).get(0);
        Rectangle2D bounds = screen.getVisualBounds();

        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {    //鼠标按下的事件
            this.oldStageX = this.primaryStage.getX();
            this.oldStageY = this.primaryStage.getY();
            this.oldScreenX = e.getScreenX();
            this.oldScreenY = e.getScreenY();
        } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {  //鼠标拖动的事件
            this.primaryStage.setX(e.getScreenX() - this.oldScreenX + this.oldStageX);
            this.primaryStage.setY(e.getScreenY() - this.oldScreenY + this.oldStageY);

        }
    }
}
