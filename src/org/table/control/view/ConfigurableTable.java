/**
 * @Copyrights G. Vaidhyanathan
 */
package org.table.control.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.i18n.swing.IconButton;
import org.i18n.swing.I18NTableRowSorter;
import org.i18n.swing.util.Utils;
import org.table.control.ControlConstants;
import org.table.control.XMLOperations;
import org.table.control.XMLTableDataModel;
import org.table.control.actions.AddAction;
import org.table.control.actions.HelpAction;
import org.table.control.actions.LoadTemplateAction;
import org.table.control.actions.OpenXMLAction;
import org.table.control.actions.RemoveAction;
import org.table.control.actions.SaveXMLAction;
import org.table.control.actions.TransformXMLAction;
import org.table.control.actions.UpdateAction;
import org.table.control.img.ImageRegistry;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class ConfigurableTable extends JPanel {

  private final JTable table;
  private final ConfigurableTableModel<?> tableModel;
  private final int objectColumnIndex;
  private final JButton loadTemplateAction;
  private final JButton loadXMLAction;
  private final JButton saveXMLAction;
  private final JButton transformXMLAction;

  private final JButton addAction;
  private final JButton removeAction;
  private final JButton updateAction;
  
  private final JLabel displayData = new JLabel();
  private final JButton helpAction;

  @SuppressWarnings( { "unchecked", "synthetic-access" })
  public ConfigurableTable(XMLOperations xmlOperations) {
    super(new BorderLayout());
    setBorder(Utils.getTitledBorder(xmlOperations.getTitle(), xmlOperations.getMessages()));

    loadTemplateAction = new JButton(new LoadTemplateAction(xmlOperations));
    loadXMLAction = new JButton(new OpenXMLAction(xmlOperations));
    saveXMLAction = new JButton(new SaveXMLAction(xmlOperations));
    transformXMLAction = new JButton(new TransformXMLAction(xmlOperations));

    addAction = new JButton(new AddAction(xmlOperations));
    removeAction = new JButton(new RemoveAction(xmlOperations));
    updateAction = new JButton(new UpdateAction(xmlOperations));
    
    helpAction = new IconButton(new HelpAction(xmlOperations));
    
    displayData.setForeground(new Color(0, 0, 106));
    displayData.setFont(ControlConstants.BOLD_FONT);
    addActions();

    this.tableModel = new ConfigurableTableModel(xmlOperations.getTableDataModel());
    xmlOperations.addTableDataListener(tableModel);
    tableModel.initColumns();

    table = new ListeningTable();
    table.setModel(tableModel);
    table.setAutoCreateColumnsFromModel(true);

    TableColumn objectColumn = table.getColumn(ControlConstants.OBJECT_COLUMN);
    objectColumnIndex = table.getColumnModel().getColumnIndex(ControlConstants.OBJECT_COLUMN);
    table.getColumnModel().removeColumn(objectColumn);

    initTable();
    add(new JScrollPane(table));
  }

  /**
   * 
   */
  private void addActions() {
    final JPanel actionNorth = new JPanel(new BorderLayout());
    
    JPanel actions = new JPanel();
    
    loadTemplateAction.setIcon(ImageRegistry.getIcon(ImageRegistry.OPEN_ICON));
    loadXMLAction.setIcon(ImageRegistry.getIcon(ImageRegistry.BROWSE_ICON));
    saveXMLAction.setIcon(ImageRegistry.getIcon(ImageRegistry.SAVE_ICON));
    transformXMLAction.setIcon(ImageRegistry.getIcon(ImageRegistry.COUNT_ICON));
    
    helpAction.setIcon(ImageRegistry.getIcon(ImageRegistry.HELP_ICON));
    helpAction.setBorder(null);
    
    saveXMLAction.setEnabled(false);
    transformXMLAction.setEnabled(false);

    actions.add(loadTemplateAction);
    actions.add(loadXMLAction);
    actions.add(saveXMLAction);
    actions.add(transformXMLAction);
    
    JPanel help = new JPanel();
    help.add(helpAction);
    
    actionNorth.add(actions, BorderLayout.CENTER);
    actionNorth.add(help, BorderLayout.EAST);

    add(actionNorth, BorderLayout.NORTH);

    addAction.setIcon(ImageRegistry.getIcon(ImageRegistry.ADD_ICON));
    updateAction.setIcon(ImageRegistry.getIcon(ImageRegistry.EDIT_ICON));
    removeAction.setIcon(ImageRegistry.getIcon(ImageRegistry.DELETE_ICON));

    addAction.setEnabled(false);
    updateAction.setEnabled(false);
    removeAction.setEnabled(false);
    
    final JPanel actionSouth = new JPanel();
    actionSouth.add(addAction);
    actionSouth.add(updateAction);
    actionSouth.add(removeAction);

    final JPanel displayPanel = new JPanel();
    displayPanel.add(displayData);
    
    final JPanel southPanel = new JPanel(new GridLayout(2, 1, 3, 3));
    southPanel.add(displayPanel);
    southPanel.add(actionSouth);
    add(southPanel, BorderLayout.SOUTH);
  }

  /**
   * Inits the sorter for the table
   */
  @SuppressWarnings("synthetic-access")
  private void initTable() {
    TableRowSorter<ConfigurableTableModel<?>> sorter = new I18NTableRowSorter<ConfigurableTableModel<?>>(tableModel);
    table.setRowSorter(sorter);
    sorter.setSortsOnUpdates(true);

    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.getSelectionModel().addListSelectionListener(new ControlListSelectionListener());
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
          updateAction.doClick();
        }
      }
    });

    ControlTableCellRenderer renderer = new ControlTableCellRenderer();
    table.setDefaultRenderer(Integer.class, renderer);
    table.setDefaultRenderer(Object.class, renderer);
  }

  private void tableSelectionChanged(boolean selected) {
    updateAction.setEnabled(!selected);
    removeAction.setEnabled(!selected);
  }

  private class ControlListSelectionListener implements ListSelectionListener {

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @SuppressWarnings("synthetic-access")
    public void valueChanged(ListSelectionEvent event) {
      ListSelectionModel model = (ListSelectionModel) event.getSource();
      boolean isSelectionEmpty = model.isSelectionEmpty();

      int index = table.getSelectedRow();
      int modelIndex = table.convertRowIndexToModel(index);
      isSelectionEmpty |= (modelIndex >= tableModel.getRowCount()-1);
      tableSelectionChanged(isSelectionEmpty);
      
      Object selectedRow = isSelectionEmpty ? null : tableModel.getValueAt(modelIndex, objectColumnIndex);
      if (model.getValueIsAdjusting()) {
        updateAction.getAction().putValue(ControlConstants.SELECTED_VALUE, selectedRow);
        removeAction.getAction().putValue(ControlConstants.SELECTED_VALUE, selectedRow);
      }
    }
  }

  private class ListeningTable extends JTable {

    /*
     * (non-Javadoc)
     * @see javax.swing.JTable#tableChanged(javax.swing.event.TableModelEvent)
     */
    @SuppressWarnings("synthetic-access")
    @Override
    public void tableChanged(TableModelEvent e) {
      super.tableChanged(e);
      boolean nonEmpty = (getRowCount() > 0);
      XMLTableDataModel<?> elt = tableModel.getTableDataModelElement();
      addAction.setEnabled(nonEmpty && elt.canAddRow());
      saveXMLAction.setEnabled(nonEmpty);
      transformXMLAction.setEnabled(nonEmpty);
      
      displayData.setText(nonEmpty ? elt.displayData() : ControlConstants.EMPTY_STRING);
    }
  }
}
