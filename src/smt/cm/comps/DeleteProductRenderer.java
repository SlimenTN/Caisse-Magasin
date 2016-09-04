package smt.cm.comps;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import smt.cm.util.AppConst;
import smt.services.Services;
import smt.services.TabModel;

public class DeleteProductRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor{
	private static final long serialVersionUID = -195777350964490765L;
	private NumpadButton render;
	private NumpadButton edit;
	
	public DeleteProductRenderer() {
		render = new NumpadButton("X");
		
		edit = new NumpadButton("X");
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Services.logConsole("current row: "+row, AppConst.DEBUG_ON);
				int r = table.convertRowIndexToModel( table.getEditingRow() );
				fireEditingStopped();
				try {
					((TabModel)table.getModel()).removeRow(r);
				} catch (Exception e2) {}
				
			}
		});
		return edit;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
		return render;
	}

}
