package cn.tealc.teafx.dialog;

import cn.tealc.teafx.stage.handler.DragWindowHandler;
import javafx.event.EventTarget;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;

/**
 * @program: TeaFX
 * @description:
 * @author: Leck
 * @create: 2024-07-09 20:33
 */
public class TeaDialog extends Dialog<ButtonType> {
    public TeaDialog() {
        initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = getDialogPane();
        Button closeButton = new Button("Close");
        StackPane stackPane = new StackPane(closeButton);

        DragDialogHandler dragDialogHandler=new DragDialogHandler(this,true);
        stackPane.setOnMouseDragged(dragDialogHandler);
        stackPane.setOnMousePressed(dragDialogHandler);
        stackPane.setOnMouseReleased(dragDialogHandler);

        stackPane.setPrefHeight(60);
        stackPane.setAlignment(Pos.CENTER_RIGHT);
        dialogPane.setHeader(stackPane);

    }
}