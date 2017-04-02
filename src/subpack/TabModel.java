package subpack;

import javax.swing.table.*;
import java.io.*;

public class TabModel extends AbstractTableModel{

    public TabModel() {
        super();
    }

    //Метод обновляет содержимое таблицы, извлекая данные из объекта folder
    public void refresh(File folder){

    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

}
