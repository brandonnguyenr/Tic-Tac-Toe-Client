package io.github.donut.proj.utils;

public final class Util {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 450;
    public static final String TITLE = "Donut Tic Tac Toe";
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static boolean isWindows() {
        return Util.OS.contains("win");
    }

    public static boolean isMac() {
        return Util.OS.contains("mac") || Util.OS.contains("darwin");
    }

    public static boolean isLinux() {
        return Util.OS.contains("nix") || Util.OS.contains("nux") || Util.OS.contains("aix");
    }
}
