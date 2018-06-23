/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.table.control.XMLOperations;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class TransformXMLAction extends AbstractXMLAction {

  public TransformXMLAction(XMLOperations<Object> operation) {
    super("transform.xml.action", operation); //$NON-NLS-1$
  }

  /*
   * (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    doSaveOperation((JComponent) e.getSource());
  }

  /**
   * @return File Filter for the save dialog
   */
  @Override
  protected FileNameExtensionFilter getFileFilter() {
    return new FileNameExtensionFilter("HTML Document", "html"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected void saveFile(String fileName, JComponent component) {
    operations.transformXML(fileName);
  }
}
