module cn.tealc.teafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.sun.jna.platform;

    opens cn.tealc.teafx.controls to javafx.fxml;
    opens cn.tealc.teafx.pane to javafx.fxml;
    opens com.jfoenixN.controls to javafx.fxml;

    exports cn.tealc.teafx.stage;
    exports cn.tealc.teafx.stage.handler;
    exports cn.tealc.teafx.controls;
    exports cn.tealc.teafx.pane;
    exports cn.tealc.teafx.theme;
    exports cn.tealc.teafx.controls.notification;
    exports cn.tealc.teafx.enums;
    exports cn.tealc.teafx.utils;
    exports cn.tealc.teafx.utils.colorThief;
    exports cn.tealc.teafx.utils.message;

    exports com.jfoenixN.controls;
    exports com.jfoenixN.transitions;
    exports com.jfoenixN.converters;


}