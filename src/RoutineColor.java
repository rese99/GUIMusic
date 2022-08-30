import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class RoutineColor extends DefaultTableCellRenderer {
    int mouseEnterRow = -1;
    int mouseEnterColumn = -1;

    public void setMouseEnterRowRow(int mouseEnterRow) {
        this.mouseEnterRow = mouseEnterRow;
    }

    public void setMouseEnterColumnColumn(int mouseEnterColumn) {
        this.mouseEnterColumn = mouseEnterColumn;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (mouseEnterRow != -1 && mouseEnterRow == row) {
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.cyan);
            setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
            setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        }
        if (value instanceof JLabel) {
            JLabel label = (JLabel) value;
            label.setOpaque(false);
            if (row == mouseEnterRow && column == mouseEnterColumn) {
                label.setForeground(Color.cyan);
            } else {
                label.setForeground(table.getForeground());
            }
            return label;
        } else
            return comp;
    }
}