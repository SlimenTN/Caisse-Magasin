package smt.cm.util;

import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.UIManager;

/**
 * Class AppConst
 * Contains all static needed variables
 * @author Slimen
 *
 */
public class AppConst {
	
	/**
	 * Activate or Diactivate debug mode
	 */
	public static final boolean DEBUG_ON = true;
	
	/**
	 * JTable's default selection color
	 */
	public static final Color jtableSelectionColor = UIManager.getColor("Table.selectionBackground");
	
	/**
	 * Tunisian default number format
	 */
	public static final DecimalFormat numberFormat = new DecimalFormat("#,##0.000");
	
	public static final String NOT_SAVED_TICKETS_FILE = "notsavedtickets.smt";
	
	/**
	 * Caisse's different stats
	 */
	public static final int CAISSE_CLOSED = 0;
	public static final int CAISSE_ACTIV = 1;
	public static final int CAISSE_SLEEP = 2;
	
	/**
	 * Cash mode
	 */
	public static final int CASH_MODE = 1;
}
