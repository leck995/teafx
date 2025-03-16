package cn.tealc.teafx.stage;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import javafx.stage.Stage;

/**
 * @program: TeaFX
 * @description:
 * @author: Leck
 * @create: 2024-10-25 21:51
 */
public class NewTeaStage extends Stage {
    private WinDef.HWND hwnd;
    private boolean init = false;
    public NewTeaStage(String title) {
        setTitle(title);

    }



    public void initAndShow(){
        show();
        hwnd=getHwnd();
        hideTitleBar(this);
    }


    public void max(){
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MAXIMIZE);
    }
    private WinDef.HWND getHwnd(){
        return User32.INSTANCE.FindWindow(null, getTitle());
    }
    private void hideTitleBar(Stage stage) {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, stage.getTitle());
        if (hwnd != null) {
            int style = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_STYLE);
            style &= ~WinUser.WS_CAPTION; // 移除标题栏样式
            User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_STYLE,WinUser. WS_BORDER);
            User32.INSTANCE.SetWindowPos(hwnd, null, 0, 0, 0, 0, WinUser.SWP_NOMOVE | WinUser.SWP_NOSIZE | WinUser.SWP_NOZORDER | WinUser.SWP_NOACTIVATE | WinUser.SWP_FRAMECHANGED);
        }
        else {
            System.out.println("HWND is null");
        }
    }
}


