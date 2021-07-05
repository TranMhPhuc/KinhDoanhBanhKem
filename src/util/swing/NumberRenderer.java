/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.swing;

/**
 *
 * @author Minh Tu
 */
import java.text.NumberFormat;
import javax.swing.SwingConstants;
import util.constant.AppConstant;

public class NumberRenderer extends FormatRenderer {

    /*
	 *  Use the specified number formatter and right align the text
     */
    public NumberRenderer(NumberFormat formatter) {
        super(formatter);
        setHorizontalAlignment(SwingConstants.RIGHT);
    }
    private static String pattern = "###,### VND";

    /*
	 *  Use the default currency formatter for the default locale
     */
    public static NumberRenderer getCurrencyRenderer() {

        return new NumberRenderer(AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER);
    }
    
    public static NumberRenderer getNumberRenderer(){
        return new NumberRenderer(AppConstant.NUMBER_FORMAT);
    }
    /*
	 *  Use the default integer formatter for the default locale
     */
    public static NumberRenderer getIntegerRenderer() {
        return new NumberRenderer(NumberFormat.getIntegerInstance());
    }

    /*
	 *  Use the default percent formatter for the default locale
     */
    public static NumberRenderer getPercentRenderer() {
        return new NumberRenderer(NumberFormat.getPercentInstance());
    }

}
