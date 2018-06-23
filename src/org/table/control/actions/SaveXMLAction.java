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
public class SaveXMLAction extends AbstractXMLAction {

  public SaveXMLAction(XMLOperations<Object> operation) {
    super("save.xml.action", operation); //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    doSaveOperation((JComponent) e.getSource());
  }

  @Override
  public void saveFile(String fileName, JComponent component) {
    try {
      operations.validateData();
      operations.saveXML(fileName);
    } catch (Exception ex) {
      DialogUtil.showErrorDialog(DialogUtil.getWindow(component), Utils.getMessage(ex));
    }
  }

}
