package cn.tealc.teafx.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef;

public interface Dwmapi extends Library {

    Dwmapi INSTANCE = Native.load("dwmapi", Dwmapi.class);

    int DwmSetWindowAttribute(WinDef.HWND hwnd, int dwAttribute, PointerType pvAttribute, int cbAttribute);

}
