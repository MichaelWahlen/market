package main.gui;

import java.util.List;

public class TableRepresentation {
	
	private List<String> columnNames;
	private Object[][] objectRepresentation;
	
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public Object[][] getObjectRepresentation() {
		return objectRepresentation;
	}
	public void setObjectRepresentation(Object[][] objectRepresentation) {
		this.objectRepresentation = objectRepresentation;
	}

}
