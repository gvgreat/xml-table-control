/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import org.i18n.swing.util.DialogUtil;
import org.table.control.XMLOperations;
import org.table.control.view.ConfigurationPanel;

/**
 * @author G. Vaidhyanathan
 *
 */
@SuppressWarnings("serial")
public class AddAction extends AbstractXMLAction {
  
  public AddAction(XMLOperations<Object> operation) {
    super("add.action", operation); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    showAddDialog((JComponent) e.getSource());
  }

  /**
   * 
   */
  private void showAddDialog(JComponent component) {
    ConfigurationPanel panel = new ConfigurationPanel(operations);
    DialogUtil.createAndShowDialog(DialogUtil.getWindow(component), messages.getString("add.row"), panel); //$NON-NLS-1$
  }

}
