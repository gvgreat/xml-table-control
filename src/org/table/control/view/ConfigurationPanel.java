/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;

import org.i18n.swing.util.DialogUtil;
import org.i18n.swing.util.Utils;
import org.table.control.ControlConstants;
import org.table.control.XMLOperations;
import org.table.control.XMLTableDataModel;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class ConfigurationPanel extends JPanel {

  private final XMLOperations<Object> xmlOperations;
  private JLabel[] labels;
  private JFormattedTextField[] textFields;
  private final boolean updateMode;
  private final Object selectedValue;
  private final DefaultFormatter formatter = new DefaultFormatter();
  private final DecimalFormat decimalFormat = new DecimalFormat("##.##"); //$NON-NLS-1$
  private final NumberFormatter numberFormatter;

  public ConfigurationPanel(XMLOperations<Object> operations) {
    this(operations, false, null);
  }

  /**
   * @param operations
   * @param b
   */
  public ConfigurationPanel(XMLOperations<Object> operations, boolean updateMode_p, Object selectedValue_p) {
    xmlOperations = operations;
    updateMode = updateMode_p;
    if (updateMode && (selectedValue_p == null)) {
      throw new IllegalArgumentException("Selected Value for update mode cannot be null"); //$NON-NLS-1$
    }
    selectedValue = updateMode ? selectedValue_p : operations.getTableDataModel().getNewElement();

    decimalFormat.setMinimumFractionDigits(2);
    decimalFormat.setMaximumFractionDigits(2);
    numberFormatter = new NumberFormatter(decimalFormat);
    formatter.setValueClass(String.class);
    numberFormatter.setValueClass(Double.class);
    init();
  }

  /**
   * 
   */
  private void init() {

    XMLTableDataModel<?> model = xmlOperations.getTableDataModel();
    List<Field> fields = Arrays.asList(model.getTypeClass().getDeclaredFields());
    setLayout(new GridLayout(fields.size() + 1, 2, 3, 3));
    labels = new JLabel[fields.size()];
    textFields = new JFormattedTextField[fields.size()];

    int cnt = 0;
    for (Field field : fields) {
      labels[cnt] = new JLabel(Utils.getCapitalized(field.getName()));
      add(labels[cnt]);

      textFields[cnt] =
                        field.getType().isPrimitive() ? new JFormattedTextField(numberFormatter)
                                                     : new JFormattedTextField(formatter);
      add(textFields[cnt]);
      textFields[cnt].setEditable(model.isEnabled(field.getName()));
      try {
        Object value =
                       selectedValue.getClass().getMethod(Utils.getAccessorMethodName(field.getName()),
                                                          ControlConstants.NULL_CLASS_ARRAY).invoke(selectedValue);
        if (value != null && value.getClass() == String.class) {
          value = ((String) value).trim();
        }
        textFields[cnt].setValue(value);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      cnt++;
    }

    add(new JButton(new AbstractAction("OK") {//$NON-NLS-1$
                      @SuppressWarnings("synthetic-access")
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        performOperation();
                      }
                    }));

    add(new JButton(new AbstractAction("Cancel") {//$NON-NLS-1$
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        DialogUtil.disposeDialog();
                      }
                    }));
  }

  private void performOperation() {
    Map<String, Object> values = prepareMap();
    Utils.populateValuesForObject(values, selectedValue);

    try {
      xmlOperations.validateData(selectedValue);
      if (updateMode) {
        xmlOperations.getTableDataModel().updateRow(selectedValue);
      } else {
        xmlOperations.getTableDataModel().addRow(selectedValue);
      }
      xmlOperations.notifyDataChanged();
    } catch (Exception ex) {
      DialogUtil.showErrorDialog(this, Utils.getMessage(ex));
      return;
    }
    DialogUtil.disposeDialog();

  }

  private Map<String, Object> prepareMap() {
    Map<String, Object> map = new HashMap<String, Object>();
    for (int i = 0; i < labels.length; i++) {
      map.put(labels[i].getText(), textFields[i].getValue());
    }
    return map;
  }
}
