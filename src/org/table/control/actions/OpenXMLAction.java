/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.i18n.swing.util.DialogUtil;
import org.i18n.swing.util.Utils;
import org.table.control.XMLOperations;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class OpenXMLAction extends AbstractXMLAction {

  public OpenXMLAction(XMLOperations<Object> operation) {
    super("open.xml.action", operation); //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    doLoadOperation((JComponent) e.getSource());
  }

  /**
   * Does the browse operation. Simply opens a file chooser
   */ 
  @SuppressWarnings("nls")
  private void doLoadOperation(JComponent component) {
    JFileChooser fileChooser = new JFileChooser();

    FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Document", "xml");
    fileChooser.addChoosableFileFilter(filter);
    fileChooser.setAcceptAllFileFilterUsed(false);

    int operation = fileChooser.showOpenDialog(DialogUtil.getWindow(component));
    switch (operation) {
      case JFileChooser.APPROVE_OPTION:
        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        try {
          operations.loadXML(fileName);
        } catch (Exception ex) {
          DialogUtil.showErrorDialog(DialogUtil.getWindow(component), Utils.getMessage(ex));
        }
      break;
      case JFileChooser.CANCEL_OPTION:
      break;
      default:
      break;
    }
  }

}
