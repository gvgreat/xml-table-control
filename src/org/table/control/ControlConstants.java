/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control;

import java.awt.Font;

/**
 * @author t_vaidhyanathan
 *
 */
public final class ControlConstants {
  public static final Font FONT = new Font("Tahoma", Font.PLAIN, 11); //$NON-NLS-1$
  public static final String OBJECT_COLUMN = "OBJECT"; //$NON-NLS-1$
  public static final String SELECTED_VALUE = "SELECTED_ROW"; //$NON-NLS-1$
  public static final String TOTAL = "TOTAL"; //$NON-NLS-1$
  public static final String EMPTY_STRING = ""; //$NON-NLS-1$
  public static final Class<?>[] NULL_CLASS_ARRAY = null;
  public static final Font BOLD_FONT = FONT.deriveFont(Font.BOLD, 12);
}
