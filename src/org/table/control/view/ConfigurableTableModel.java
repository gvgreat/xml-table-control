/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.view;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.i18n.swing.util.Utils;
import org.table.control.ControlConstants;
import org.table.control.XMLTableDataListener;
import org.table.control.XMLTableDataModel;

/**
 * <class>ConfigurableTableModel</class> builds the table model from TableElement interface.
 * @author G. Vaidhyanathan
 */
public final class ConfigurableTableModel<T> extends DefaultTableModel implements XMLTableDataListener {

  private static final long serialVersionUID = -7378534271949617354L;

  private XMLTableDataModel<T> dataModelElement;

  public ConfigurableTableModel(XMLTableDataModel<T> tableElement) {
    super();
    dataModelElement = tableElement;
  }

  @SuppressWarnings("unchecked")
  public void initColumns() {
    columnIdentifiers.addAll(dataModelElement.getColumnNames());
    columnIdentifiers.add(ControlConstants.OBJECT_COLUMN);
  }

  /**
   * Populates the table model
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws SecurityException
   */
  @SuppressWarnings( { "unchecked", "boxing" })
  private void populateData() throws IllegalArgumentException, IllegalAccessException, SecurityException,
      InvocationTargetException, NoSuchMethodException {
    dataVector.clear();
    Vector<Vector<?>> allRows = new Vector<Vector<?>>(1);
    for (Object factor : dataModelElement.getAllRows()) {
      Vector<Object> oneRow = new Vector<Object>();
      Class<? extends Object> factorClass = factor.getClass();
      for (Field field : factorClass.getDeclaredFields()) {
        oneRow.add(factorClass.getMethod(Utils.getAccessorMethodName(field.getName()),
                                         ControlConstants.NULL_CLASS_ARRAY).invoke(factor));
      }
      oneRow.add(factor);

      allRows.add(oneRow);
    }
    Vector<Object> totalRow = new Vector(1);
    int size = columnIdentifiers.size();
    for (int i = 0; i < size - 3; i++) {
      totalRow.add(ControlConstants.EMPTY_STRING);
    }
    totalRow.add(ControlConstants.TOTAL);
    totalRow.add(dataModelElement.getTotalValue());
    allRows.add(totalRow);

    dataVector.addAll(allRows);
    fireTableDataChanged();
  }

  /*
   * (non-Javadoc)
   * @see org.table.control.TableDataListener#dataChanged()
   */
  @SuppressWarnings("unchecked")
  @Override
  public void dataChanged(XMLTableDataModel dataModelElement_p) {
    try {
      dataModelElement = dataModelElement_p;
      populateData();
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    } catch (SecurityException ex) {
      ex.printStackTrace();
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (InvocationTargetException ex) {
      ex.printStackTrace();
    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
   */
  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  /**
   * @return the element
   */
  public XMLTableDataModel<T> getTableDataModelElement() {
    return dataModelElement;
  }
}
