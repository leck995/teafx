package cn.tealc.teafx;

import cn.tealc.teafx.controls.TitleBar;
import cn.tealc.teafx.enums.TitleBarStyle;
import cn.tealc.teafx.pane.BlurStackPane;
import cn.tealc.teafx.stage.RoundStage;
import com.jfoenixN.controls.JFXDialog;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class HelloApplication extends Application {
    //测试程序
    @Override
    public void start(Stage stage) {
        stage.close();
        RoundStage roundStage=new RoundStage();
        TitleBar titleBar=new TitleBar(roundStage, TitleBarStyle.ALL);

        StackPane pane=new StackPane();

        Button button = new Button("Hello World");

        button.setOnAction(event -> {
            JFXDialog dialog = new JFXDialog();
            dialog.setDialogContainer(pane);
            dialog.setContent(new Label("11111111111"));
            dialog.show();
        });


        pane.getChildren().add(button);


        titleBar.setContent(pane);
        roundStage.setWidth(600);
        roundStage.setHeight(400);
        roundStage.setContent(titleBar);
        roundStage.setTitle("Hello!");
        roundStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}