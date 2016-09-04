package smt.cm.comps;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TicketRenderer extends AbstractCellEditor implements TableCellRenderer{
	private static final long serialVersionUID = 6341578835532672768L;
	
	private TicketRendererView render;
	
	public TicketRenderer() {
		render = new TicketRendererView();
	}

	@Override
	public Object getCellEditorValue() {
		return render.getTicketRendererObject();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
		render.setTicketRendererObject((TicketRendererObject) value);
		render.switchSelection(isSelected);
		return render;
	}

}
