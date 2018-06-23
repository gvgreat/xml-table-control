/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JComponent;

import org.i18n.swing.util.DialogUtil;
import org.table.control.ControlConstants;
import org.table.control.XMLOperations;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class HelpAction extends AbstractXMLAction {

  public HelpAction(XMLOperations<Object> operation) {
    super(ControlConstants.EMPTY_STRING, operation);
  }

  /*
   * (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      File file = new File(System.getProperty("user.home") + File.separator + "help-file.html"); //$NON-NLS-1$//$NON-NLS-2$
      InputStream in = operations.getHelpFile();
      OutputStream out = new FileOutputStream(file);
      byte buf[] = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      out.flush();
      out.close();
      in.close();
      Desktop.getDesktop().open(file);
    } catch (Exception ex) {
      DialogUtil.showErrorDialog(DialogUtil.getWindow((JComponent) e.getSource()), messages
          .getString("help.doc.not.found")); //$NON-NLS-1$
    }
  }

}
