package main.gui;
import java.util.List;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class CustomTableModel extends AbstractTableModel {
	
	private String[] columnNames;	
	private Object[][] contents;
	
	/**  
	 * Assumes the object array to have objects with proper toString representations
	 * @param contents tabular representation of the contents of the table, obj[x][y] where x represents nr of rows, and y represents the columns
	 * @param columnNames names of the columns 	
	 */
	public CustomTableModel(Object[][] contents, List<String> columnNames) {
		this.contents = contents;		
		this.columnNames = columnNames.toArray(new String[columnNames.size()]);
	}
	
	/** 
	 * @return row count
	 */
	@Override
	public int getRowCount() {		
		return contents.length;
	}
	
	/** 
	 * @return column count
	 */
	@Override
	public int getColumnCount() {		
		return columnNames.length;
	}
	
	/** 
	 * @param index column index
	 * @return column name	
	 */
	@Override
	public String getColumnName(int index) {
	    return columnNames[index];
	}
	
	/** 
	 * Data elements start at {0,0}
	 * @param rowIndex row nr
	 * @param columnIndex column nr
	 * @return the element at {row, column}
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {		
		return contents[rowIndex][columnIndex];
	}

}
