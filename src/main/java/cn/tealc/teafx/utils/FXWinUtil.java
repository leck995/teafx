package cn.tealc.teafx.utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FXWinUtil {

    /**
     * 获取句柄,
     * 如果您使用 Java 9 模块，则还需要打开调用模块的包：
     * --add-opens javafx.graphics/javafx.stage=com.example和--add-opens javafx.graphics/com.sun.javafx.tk.quantum=com.example
     * @param stage
     */
    public static WinDef.HWND getNativeHandleForStage(Stage stage) {
        try {
            var getPeer = Window.class.getDeclaredMethod("getPeer", null);
            getPeer.setAccessible(true);
            var tkStage = getPeer.invoke(stage);
            var getRawHandle = tkStage.getClass().getMethod("getRawHandle");
            getRawHandle.setAccessible(true);
            var pointer = new Pointer((Long) getRawHandle.invoke(tkStage));
            return new WinDef.HWND(pointer);
        } catch (Exception ex) {
            System.err.println("Unable to determine native handle for window");
            System.out.println(ex.getMessage());
            return null;
        }

    }


    /**
     * jna获取句柄
     * @param stage
     * @param title
     */
    public static WinDef.HWND getWindowHandleForStage(Stage stage,String title) {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, title);
        return hwnd;
    }

    /**
     * 设置标题栏模式，需要stage设置好具有唯一性的标题
     * @param stage
     * @param darkMode
     * @param forceRedrawOfWindowTitleBar
     */
    public static void setDarkModeByTitle(Stage stage, boolean darkMode,boolean forceRedrawOfWindowTitleBar) {
        var hwnd = FXWinUtil.getWindowHandleForStage(stage,stage.getTitle());
        var dwmapi = Dwmapi.INSTANCE;
        WinDef.BOOLByReference darkModeRef = new WinDef.BOOLByReference(new WinDef.BOOL(darkMode));
        dwmapi.DwmSetWindowAttribute(hwnd, 20, darkModeRef, Native.getNativeSize(WinDef.BOOLByReference.class));
        if (forceRedrawOfWindowTitleBar) {
            forceRedrawOfWindowTitleBar(stage);
        }
    }


    public static void setDarkMode(Stage stage, boolean darkMode,boolean forceRedrawOfWindowTitleBar) {
        var hwnd = FXWinUtil.getNativeHandleForStage(stage);
        var dwmapi = Dwmapi.INSTANCE;
        WinDef.BOOLByReference darkModeRef = new WinDef.BOOLByReference(new WinDef.BOOL(darkMode));
        dwmapi.DwmSetWindowAttribute(hwnd, 20, darkModeRef, Native.getNativeSize(WinDef.BOOLByReference.class));
        if (forceRedrawOfWindowTitleBar) {
            forceRedrawOfWindowTitleBar(stage);
        }
    }


    /**
     * 强制重绘窗口
     * @param stage
     */
    private static void forceRedrawOfWindowTitleBar(Stage stage) {
        var maximized = stage.isMaximized();
        stage.setMaximized(!maximized);
        stage.setMaximized(maximized);
    }

}
