package cn.tealc.teafx.utils;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AnchorPaneUtil {
    public static void setPosition(Node pane, Double top, Double right, Double bottom, Double left){
        AnchorPane.setTopAnchor(pane,top);
        AnchorPane.setRightAnchor(pane,right);
        AnchorPane.setBottomAnchor(pane,bottom);
        AnchorPane.setLeftAnchor(pane,left);
    }

    public static void setPosition(Node pane, int top, int right, int bottom, int left){
        AnchorPane.setTopAnchor(pane, (double) top);
        AnchorPane.setRightAnchor(pane, (double) right);
        AnchorPane.setBottomAnchor(pane, (double) bottom);
        AnchorPane.setLeftAnchor(pane, (double) left);
    }

    public static void setPosition(Node pane, int position){
        setPosition(pane,position,position,position,position);
    }
}