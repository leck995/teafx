package cn.tealc.teafx;

import cn.tealc.teafx.controls.TitleBar;
import cn.tealc.teafx.enums.TitleBarStyle;
import cn.tealc.teafx.stage.RoundStage;
import com.jfoenixN.controls.JFXDialog;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    //测试程序
    @Override
    public void start(Stage stage) throws IOException {
        stage.close();
        RoundStage roundStage=new RoundStage();
        TitleBar titleBar=new TitleBar(roundStage, TitleBarStyle.ALL);
        Button b=new Button();
        StackPane pane=new StackPane(b);



        b.setOnAction(event -> {
            JFXDialog jfxDialog=new JFXDialog(pane,new StackPane(new Button("222")), JFXDialog.DialogTransition.BOTTOM);
            jfxDialog.show();

        });

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