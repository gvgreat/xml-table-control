/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control;

import java.util.EventListener;

/**
 * @author G. Vaidhyanathan
 *
 */
public interface XMLTableDataListener extends EventListener {
  
  /**
   * Specifies the data for TableModel has changed
   */
  void dataChanged(XMLTableDataModel<?> element);
}
