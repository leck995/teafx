package cn.tealc.teafx.theme;



public final class PrimerDark implements Theme {
    public PrimerDark() {
    }

    public String getName() {
        return "Primer Dark";
    }

    public String getUserAgentStylesheet() {
        return this.getClass().getResource("/cn/tealc/teafx/css/theme/dark.css").toExternalForm();
    }

    public String getUserAgentStylesheetBSS() {
        return null;
    }

    public boolean isDarkMode() {
        return true;
    }
}
