package util.constant;

import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

public interface AppConstant {

    public static int SEARCH_SCORE_CUT_OFF = 60;

    //==========================================================================
    
    public static final Color COLOR_MENU_MOUSE_ENTER = new Color(107, 162, 249);
    public static final Color COLOR_MENU_MOUSE_EXIT = new Color(113, 168, 255);
    public static final Color COLOR_MENU_MOUSE_PRESS = new Color(77, 128, 216);

    //==========================================================================
    
    public static final Font MAIN_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font AXIS_TITLE_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font AXIS_TICK_TITLE_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font TOOL_TIP_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Color SERI_1_COLOR = new Color(248, 118, 109);
    public static final Font LEGEND_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font SUM_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font DELETE_FONT = new Font("Segoe UI", Font.BOLD, 2);
    
    //==========================================================================
    
    public static final DateTimeFormatter GLOBAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter GLOBAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

}
