/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.Component;
import java.io.File;
import java.text.MessageFormat;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.i18n.swing.I18NAbstractAction;
import org.i18n.swing.locale.AbstractI18NMessages;
import org.i18n.swing.util.DialogUtil;
import org.table.control.XMLOperations;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public abstract class AbstractXMLAction extends I18NAbstractAction {

  protected final XMLOperations<Object> operations;

  public AbstractXMLAction(String name, AbstractI18NMessages msg, XMLOperations<Object> operation) {
    super(name, msg);
    this.operations = operation;
  }

  public AbstractXMLAction(String name, XMLOperations<Object> operation) {
    this(name, Messages.getInstance(), operation);
  }

  @SuppressWarnings("nls")
  protected final void doSaveOperation(JComponent component) {
    JFileChooser fileChooser = new JFileChooser();

    FileNameExtensionFilter filter = getFileFilter();
    fileChooser.addChoosableFileFilter(filter);
    fileChooser.setAcceptAllFileFilterUsed(false);
    Component window = DialogUtil.getWindow(component);
    int operation = fileChooser.showSaveDialog(window);
    switch (operation) {
      case JFileChooser.APPROVE_OPTION:
        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        File file = new File(fileName);
        boolean flag = false;
        if (file.exists()) {
          int option =
                       JOptionPane.showConfirmDialog(window, MessageFormat.format(messages
                           .getString("file.already.exists.question"), file.getName()), messages
                           .getString("file.already.exists.title"), JOptionPane.WARNING_MESSAGE,
                                                     JOptionPane.OK_CANCEL_OPTION);
          flag = (option == JOptionPane.OK_OPTION);
        } else {
          fileName += "." + filter.getExtensions()[0];
          flag = true;
        }
        if (flag) {
          saveFile(fileName, component);
        }
      break;
      case JFileChooser.CANCEL_OPTION:
      break;

      default:
      break;
    }
  }

  /**
   * @return File Filter for the save dialog
   */
  protected FileNameExtensionFilter getFileFilter() {
    return new FileNameExtensionFilter("XML Document", "xml"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  protected void saveFile(String fileName, JComponent component) {
    // do nothing here...
  }
}
