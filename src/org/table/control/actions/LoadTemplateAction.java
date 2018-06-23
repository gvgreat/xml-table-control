/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import org.i18n.swing.util.DialogUtil;
import org.i18n.swing.util.Utils;
import org.table.control.XMLOperations;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class LoadTemplateAction extends AbstractXMLAction {

  public LoadTemplateAction(XMLOperations<Object> operation) {
    super("load.template.action", operation); //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      operations.loadXMLTemplate();
    } catch (Exception ex) {
      ex.printStackTrace();
      DialogUtil.showErrorDialog(DialogUtil.getWindow((JComponent) e.getSource()), Utils.getMessage(ex));
    }
  }

}
