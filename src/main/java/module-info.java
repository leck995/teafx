module cn.tealc.teafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens cn.tealc.teafx to javafx.fxml;
    opens cn.tealc.teafx.controls to javafx.fxml;
    opens com.jfoenix.controls to javafx.fxml;
    exports cn.tealc.teafx;
    exports cn.tealc.teafx.stage;
    exports cn.tealc.teafx.stage.handler;
    exports cn.tealc.teafx.controls;
    exports cn.tealc.teafx.controls.notification;
    exports cn.tealc.teafx.enums;
    exports cn.tealc.teafx.utils;

    exports com.jfoenix.controls;
    exports com.jfoenix.transitions;
    exports com.jfoenix.converters;

}