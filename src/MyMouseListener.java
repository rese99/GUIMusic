import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {
    JTable table;
    RoutineColor routineColor;

    public MyMouseListener(JTable table, RoutineColor routineColor) {
        this.table = table;
        this.routineColor = routineColor;
    }
    public void mouseMoved(MouseEvent e) {
        Point mousepoint = e.getPoint();
        this.routineColor.setMouseEnterRowRow(table.rowAtPoint(mousepoint));
        this.routineColor.setMouseEnterColumnColumn(table.columnAtPoint(mousepoint));
        this.table.repaint();
        super.mouseMoved(e);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        this.routineColor.setMouseEnterRowRow(table.getRowCount()+1);
        this.routineColor.setMouseEnterColumnColumn(table.getColumnCount()+1);
        this.table.repaint();
        super.mouseExited(e);
    }
}