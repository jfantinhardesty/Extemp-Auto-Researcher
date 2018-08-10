package extemp;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * FileSelector is responsible for the create of the JTable that will display
 * the information about every article to the user. It will handle the table
 * setup.
 */
public class FileSelector {

  /**
   * Table that will display the number, path, title and score for each article.
   */
  private static JTable table;

  /**
   * The table model that will handle the table data.
   */
  private static TableModel dataModel;

  /**
   * Scroll pane that adds the scroll bar.
   */
  private static JScrollPane tableScrollPane;

  /**
   * Table data that will store the number, path, title and score for the
   * articles.
   */
  private static List<List<Object>> tableData;

  /**
   * Stores the jTextArea that will display the article to the user.
   */
  private TextDisplayer textDisplay;

  /**
   * Creates the table and formats the table.
   * 
   * @param textDisplay
   *          Used to display the article text to the user
   */
  public FileSelector(TextDisplayer textDisplay) {
    table = new JTable();
    tableData = new ArrayList<List<Object>>();
    this.textDisplay = textDisplay;
    initComponents();
  }

  /**
   * Initializes the width and the dimensions of the table and sets the mode so
   * only rows can be selected.
   */
  private void initComponents() {

    setTableModel();

    table.getColumnModel().getColumn(0).setPreferredWidth(50);
    table.getColumnModel().getColumn(1).setPreferredWidth(400);
    table.getColumnModel().getColumn(2).setPreferredWidth(100);
    table.getColumnModel().getColumn(3).setPreferredWidth(50);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    tableScrollPane = new JScrollPane(table);
    tableScrollPane.setPreferredSize(new Dimension(600, 150));
    tableScrollPane.setMinimumSize(new Dimension(600, 150));
    table.setAutoCreateRowSorter(true);

    // Only allow the entire row to be selected
    table.setRowSelectionAllowed(true);

    // Prevent column order from changing by the user
    table.getTableHeader().setReorderingAllowed(false);

    // On a right mouse click, the article will be displayed in the text area
    table.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        textDisplay.updateText(table.getValueAt(selectedRow, 1).toString());
      }
    });

  }

  /**
   * Creates the table model that will handle the data for the table.
   */
  private void setTableModel() {
    final List<Object> columnNames = new ArrayList<Object>(
        Arrays.asList("Number ", "Path", "Title", "Score"));

    dataModel = new AbstractTableModel() {
      private static final long serialVersionUID = 1L;

      public int getColumnCount() {
        return columnNames.size();
      }

      public int getRowCount() {
        return tableData.size();
      }

      public Object getValueAt(int row, int col) {
        return tableData.get(row).get(col);
      }

      public String getColumnName(int column) {
        return (String) columnNames.get(column);
      }

      public Class<?> getColumnClass(int column) {
        switch (column) {
          case 0:
            return Integer.class;
          case 1:
            return String.class;
          case 2:
            return String.class;
          case 3:
            return Double.class;
          default:
            return null;
        }
      }
    };

    table.setModel(dataModel);

    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
  }

  /**
   * Updates the table when a new search is executed.
   * 
   * @param newData
   *          Information for the articles when a new search is executed.
   */
  public static void updateData(List<List<Object>> newData) {
    tableData = newData;
    ((AbstractTableModel) table.getModel()).fireTableDataChanged();
  }

  /**
   * Returns the scroll pane for the table because that holds the actual table
   * inside of it. We always want the scroll option to be available to the table
   * so we return this.
   * 
   * @return tableScrollPane
   */
  public JScrollPane getTable() {
    return tableScrollPane;
  }
}