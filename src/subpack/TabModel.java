package subpack;

import javax.swing.table.*;
import java.io.*;
import java.util.*;

public class TabModel extends AbstractTableModel{

    public static final int SORT_BY_NAME=0;
    public static final int SORT_BY_TYPE=1;
    public static final int SORT_BY_CREATE_DATE=3;
    public static final int SORT_BY_MODIFIED_DATE=4;

    private int directionSort=0;    //Направление сортировки. 0 - по возрастанию. 1 - по убыванию.

    private final int colCount=4;                                                         //Количество столбцов
    private final String[] colNames={"Имя", "Тип", "Дата создания", "Дата изменения"};    //Имена столбцов

    private final LinkedList<File> fileNames=new LinkedList<>();      //Массив имен файов и каталогов
    private final LinkedList<String> types=new LinkedList<>();        //Массив типов файлов (расширений)
    private final LinkedList<Date> createDates=new LinkedList<>();    //Массив дат создания
    private final LinkedList<Date> modifiedDate=new LinkedList<>();   //Массив дат модификации

    public TabModel(File folder) {
        super();
        refresh(folder, SORT_BY_NAME);
    }

    //Метод обновляет содержимое таблицы, извлекая данные из объекта folder
    public final void refresh(File folder, int sortedCol){
        if(sortedCol<0 | sortedCol>4)return;
        fileNames.clear();
        types.clear();
        createDates.clear();
        modifiedDate.clear();
        File[] lst=folder.listFiles();
        if(lst==null)return;
        if(lst.length==0)return;

    }

    @Override
    public int getRowCount() {
        return fileNames.size();
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public String getColumnName(int column){
        return colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex==0)return fileNames.get(rowIndex);
        if(columnIndex==1)return types.get(rowIndex);
        if(columnIndex==2)return createDates.get(rowIndex);
        if(columnIndex==3)return modifiedDate.get(rowIndex);
        return new Object();
    }

}
