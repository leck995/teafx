package cn.tealc.teafx.utils;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AnchorPaneUtil {
    public static void setPosition(Node pane, int position){
        setPosition(pane,position,position,position,position);
    }
    public static void setPosition(Node pane, double position){
        setPosition(pane,position,position,position,position);
    }
    public static void setPosition(Node pane, int top, int right, int bottom, int left){
        setPosition(pane,(double) top, (double) right, (double) bottom, (double) left);
    }
    public static void setPosition(Node pane, Double top, Double right, Double bottom, Double left){
        AnchorPane.setTopAnchor(pane,top);
        AnchorPane.setRightAnchor(pane,right);
        AnchorPane.setBottomAnchor(pane,bottom);
        AnchorPane.setLeftAnchor(pane,left);
    }
}