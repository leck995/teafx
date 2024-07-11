package cn.tealc.teafx.controls;


import cn.tealc.teafx.enums.TitleBarStyle;
import cn.tealc.teafx.stage.RoundStage;
import cn.tealc.teafx.stage.handler.DragWindowHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @program: AzurLaneWikiGetTool
 * @description:
 * @author: Leck
 * @create: 2023-06-03 09:07
 */
public class TitleBar extends StackPane {
    //private RoundStage stage;
    private StackPane icon;
    private HBox leftHBox,rightHBox;
    private Label title;
    private Button closeBtn,minBtn;
    private ToggleButton maxBtn,fullScreenBtn;
    private AnchorPane titleBarPane;
    private AnchorPane contentPane;
    private BoundingBox maximizedBox;

    private SimpleBooleanProperty hasInsert = new SimpleBooleanProperty(false);

    private double insertSize=5.0;
    private Color bgColor;

    public TitleBar() {
        AnchorPane mainPane=new AnchorPane();
        titleBarPane=new AnchorPane();
        titleBarPane.setPrefHeight(60.0);
        titleBarPane.getStyleClass().add("title-header");
        titleBarPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount()==2){
                if (maxBtn != null){
                    maxBtn.setSelected(!maxBtn.isSelected());
                }
            }
        });

        /*标题栏左边*/
        icon=new StackPane();
        icon.getStyleClass().add("icon");
        title=new Label();
        leftHBox=new HBox(icon,title);
        leftHBox.setAlignment(Pos.CENTER_LEFT);
        leftHBox.setSpacing(10);
        leftHBox.setPadding(new Insets(0,0,0,15.0));
        leftHBox.getStyleClass().add("left-header");
        title.getStyleClass().add("title");
        //icon.getStyleClass().add("icon");
        titleBarPane.getChildren().add(leftHBox);
        setPosition(leftHBox,0.0,null,0.0,0.0);
        /*标题栏右边边*/
        closeBtn = new Button();
        closeBtn.setGraphic(new Region());
        maxBtn = new ToggleButton();
        maxBtn.setGraphic(new Region());
        fullScreenBtn=new ToggleButton();
        fullScreenBtn.setGraphic(new Region());
        minBtn = new Button();
        minBtn.setGraphic(new Region());

        maxBtn.disableProperty().bind(fullScreenBtn.selectedProperty());


        minBtn.setOnMouseClicked(mouseEvent -> {
            Node node=(Node) mouseEvent.getSource();
            Stage stage1 =(Stage)node.getScene().getWindow();
            stage1.setIconified(true);
        });

        rightHBox=new HBox(fullScreenBtn,minBtn,maxBtn,closeBtn);

        fullScreenBtn.disableProperty().bind(maxBtn.selectedProperty());

        rightHBox.setSpacing(5.0);
        rightHBox.setPadding(new Insets(0,10,0,0));
        rightHBox.setAlignment(Pos.CENTER_RIGHT);
        rightHBox.getStyleClass().add("right-header");
        maxBtn.getStyleClass().add("max-btn");
        minBtn.getStyleClass().add("min-btn");
        closeBtn.getStyleClass().add("close-btn");
        fullScreenBtn.getStyleClass().add("full-btn");
        titleBarPane.getChildren().add(rightHBox);
        setPosition(rightHBox,0.0,0.0,0.0,null);
        getStylesheets().add(this.getClass().getResource("/cn/tealc/teafx/css/title-bar.css").toExternalForm());

        contentPane=new AnchorPane();
        contentPane.getStyleClass().add("main-content");
        mainPane.getChildren().addAll(contentPane,titleBarPane);
        setPosition(titleBarPane,0.0,0.0,null,0.0);
        setPosition(contentPane,titleBarPane.getPrefHeight(),0.0,0.0,0.0);
        mainPane.getStyleClass().add("title-bar");
        getChildren().add(mainPane);
    }

    public void init(RoundStage stage){
        DragWindowHandler handler=new DragWindowHandler(stage,true);
        titleBarPane.setOnMousePressed(handler);
        titleBarPane.setOnMouseDragged(handler);
        titleBarPane.setOnMouseReleased(handler);
        titleBarPane.setOnMouseClicked(handler);
        maxBtn.selectedProperty().bindBidirectional(stage.maxedProperty());
        fullScreenBtn.selectedProperty().bindBidirectional(stage.fullScreenedProperty());
        closeBtn.setOnAction(event -> stage.close());
    }

    public TitleBar(RoundStage stage,TitleBarStyle style) {
        AnchorPane mainPane=new AnchorPane();
        titleBarPane=new AnchorPane();
        titleBarPane.setPrefHeight(60.0);
        titleBarPane.getStyleClass().add("title-header");
        titleBarPane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount()==2){
                if (maxBtn != null){
                    maxBtn.setSelected(!maxBtn.isSelected());
                }
            }
        });
        DragWindowHandler handler=new DragWindowHandler(stage,true);
        titleBarPane.setOnMousePressed(handler);
        titleBarPane.setOnMouseDragged(handler);
        titleBarPane.setOnMouseReleased(handler);
        titleBarPane.setOnMouseClicked(handler);




        if (TitleBarStyle.ALL==style || TitleBarStyle.NO_RIGHT==style){
            /*标题栏左边*/
            icon=new StackPane();
            icon.getStyleClass().add("icon");
            title=new Label();
            leftHBox=new HBox(icon,title);
            leftHBox.setAlignment(Pos.CENTER_LEFT);
            leftHBox.setSpacing(10);
            leftHBox.setPadding(new Insets(0,0,0,15.0));
            leftHBox.getStyleClass().add("left-header");
            title.getStyleClass().add("title");
            //icon.getStyleClass().add("icon");
            titleBarPane.getChildren().add(leftHBox);
            setPosition(leftHBox,0.0,null,0.0,0.0);
        }

        if (TitleBarStyle.ALL==style || TitleBarStyle.NO_LEFT==style) {
            /*标题栏右边边*/
            closeBtn = new Button();
            closeBtn.setGraphic(new Region());
            maxBtn = new ToggleButton();
            maxBtn.setGraphic(new Region());
            fullScreenBtn=new ToggleButton();
            fullScreenBtn.setGraphic(new Region());
            minBtn = new Button();
            minBtn.setGraphic(new Region());
            maxBtn.selectedProperty().bindBidirectional(stage.maxedProperty());
            maxBtn.disableProperty().bind(fullScreenBtn.selectedProperty());


            minBtn.setOnMouseClicked(mouseEvent -> {
                Node node=(Node) mouseEvent.getSource();
                Stage stage1 =(Stage)node.getScene().getWindow();
                stage1.setIconified(true);
            });
            closeBtn.setOnAction(event -> stage.close());
            rightHBox=new HBox(fullScreenBtn,minBtn,maxBtn,closeBtn);
            fullScreenBtn.selectedProperty().bindBidirectional(stage.fullScreenedProperty());
            fullScreenBtn.disableProperty().bind(maxBtn.selectedProperty());

            rightHBox.setSpacing(5.0);
            rightHBox.setPadding(new Insets(0,10,0,0));
            rightHBox.setAlignment(Pos.CENTER_RIGHT);
            rightHBox.getStyleClass().add("right-header");
            maxBtn.getStyleClass().add("max-btn");
            minBtn.getStyleClass().add("min-btn");
            closeBtn.getStyleClass().add("close-btn");
            fullScreenBtn.getStyleClass().add("full-btn");
            titleBarPane.getChildren().add(rightHBox);
            setPosition(rightHBox,0.0,0.0,0.0,null);
            getStylesheets().add(this.getClass().getResource("/cn/tealc/teafx/css/title-bar.css").toExternalForm());
        }

        contentPane=new AnchorPane();
        contentPane.getStyleClass().add("main-content");
        mainPane.getChildren().addAll(contentPane,titleBarPane);
        setPosition(titleBarPane,0.0,0.0,null,0.0);
        setPosition(contentPane,titleBarPane.getPrefHeight(),0.0,0.0,0.0);
        mainPane.getStyleClass().add("title-bar");
        getChildren().add(mainPane);

    }






    /**
     * @name: setPosition
     * @description: 简化AnchorPane的位置设置
     * @author: Leck
     * @param:	pane
     * @param:	top
     * @param:	right
     * @param:	bottom
     * @param:	left
     * @return  void
     * @date:   2023/1/2
     */
    private void setPosition(Node pane, Double top, Double right, Double bottom, Double left){
        AnchorPane.setTopAnchor(pane,top);
        AnchorPane.setRightAnchor(pane,right);
        AnchorPane.setBottomAnchor(pane,bottom);
        AnchorPane.setLeftAnchor(pane,left);
    }

    public void setContent(Node node){
        contentPane.getChildren().add(node);
        setPosition(node,0.0,0.0,0.0,0.0);
    }


    public HBox getTitleBarLeftPane(){
        return leftHBox;
    }

    public HBox getTitleBarRightPane(){
        return rightHBox;
    }

    public void setIcon(Node node){
        icon.getChildren().add(node);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }
    public void setTitleIcon(Node node){
        this.title.setGraphic(node);
    }

    public void setRoot(Node node){
        getChildren().add(node);
        setPosition(node,0.0,0.0,0.0,0.0);
    }


    public Button close(){
        return closeBtn;
    }

    public void setCloseEvent(EventHandler<ActionEvent> eventHandler){
        closeBtn.setOnAction(eventHandler);
    }


    public Label title(){
        return title;
    }

    public AnchorPane getContent() {
        return contentPane;
    }


}