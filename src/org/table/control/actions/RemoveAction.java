/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.actions;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.i18n.swing.util.DialogUtil;
import org.i18n.swing.util.Utils;
import org.table.control.ControlConstants;
import org.table.control.XMLOperations;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class RemoveAction extends AbstractXMLAction {

	public RemoveAction(XMLOperations<Object> operation) {
		super("remove.action", operation); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent button = (JComponent) e.getSource();
		Object selectedValue = getValue(ControlConstants.SELECTED_VALUE);
		int remove = JOptionPane
				.showConfirmDialog(
						DialogUtil.getWindow(button),
						MessageFormat.format(messages.getString("remove.row.question"), selectedValue), messages.getString("remove.row"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
		switch (remove) {
		case JOptionPane.YES_OPTION:
			try {
				operations.validateData(selectedValue);
				operations.getTableDataModel().removeRow(selectedValue);
			} catch (Exception ex) {
				DialogUtil.showErrorDialog(DialogUtil.getWindow(button), Utils.getMessage(ex));
			}
			break;
		case JOptionPane.NO_OPTION:
			break;
		default:
			break;
		}
	}

}
