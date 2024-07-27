package cn.tealc.teafx.theme;


import javafx.scene.effect.Light;

public final class PrimerLight implements Theme {
    public PrimerLight() {
    }

    public String getName() {
        return "Primer Light";
    }

    public String getUserAgentStylesheet() {
        return this.getClass().getResource("/cn/tealc/teafx/css/theme/light.css").toExternalForm();
    }

    public String getUserAgentStylesheetBSS() {
        return null;
    }

    public boolean isDarkMode() {
        return false;
    }
}
