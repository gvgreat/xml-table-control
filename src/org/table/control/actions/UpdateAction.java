/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import org.i18n.swing.util.DialogUtil;
import org.table.control.ControlConstants;
import org.table.control.XMLOperations;
import org.table.control.view.ConfigurationPanel;

/**
 * @author G. Vaidhyanathan
 *
 */
@SuppressWarnings("serial")
public class UpdateAction extends AbstractXMLAction {
  
  public UpdateAction(XMLOperations<Object> operation) {
    super("update.action", operation); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    showUpdateDialog((JComponent) e.getSource());
  }

  /**
   * 
   */
  private void showUpdateDialog(JComponent component) {
    ConfigurationPanel panel = new ConfigurationPanel(operations, true, getValue(ControlConstants.SELECTED_VALUE));
    DialogUtil.createAndShowDialog(DialogUtil.getWindow(component), messages.getString("update.row"), panel); //$NON-NLS-1$
  }
}
