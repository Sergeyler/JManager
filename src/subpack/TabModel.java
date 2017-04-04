package subpack;

import javax.swing.table.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.*;

public class TabModel extends AbstractTableModel{

    private final int colCount=4;                                                         //Количество столбцов
    private final String[] colNames={"Имя", "Тип", "Дата создания", "Дата изменения"};    //Имена столбцов

    private int folderPos=(-1);    //Граница списка папок в списках атрибутов

    private final LinkedList<File> fileNames=new LinkedList<>();      //Массив имен файов и каталогов
    private final LinkedList<String> types=new LinkedList<>();        //Массив типов файлов (расширений)
    private final LinkedList<Date> createDates=new LinkedList<>();    //Массив дат создания
    private final LinkedList<Date> modifiedDate=new LinkedList<>();   //Массив дат модификации

    public TabModel(File folder) {
        super();
        refresh(folder, true);
    }

    //Метод обновляет содержимое таблицы, извлекая данные из объекта folder
    public final void refresh(File folder, boolean hiddenEnabled){
        //Сбасываем значения атрибутов файлов
        fileNames.clear();
        types.clear();
        createDates.clear();
        modifiedDate.clear();
        folderPos=(-1);

        //Получаем список файлов
        File[] lst=folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isHidden()?hiddenEnabled:true;
            }
        });
        if(lst==null)return;
        if(lst.length==0)return;

        //Формируем списки атрибутов
        Date dc;    //Даты создания
        Date dm;    //Даты модификации
        for(File f: lst){
            try {
                dc=new Date(((FileTime)Files.getAttribute(f.toPath(), "creationTime")).toMillis());
            } catch (IOException ex) {
                dc=new Date(0);
            }
            try {
                dm=new Date(((FileTime)Files.getAttribute(f.toPath(), "lastModifiedTime")).toMillis());
            } catch (IOException ex) {
                dm=new Date(0);
            }
            if(f.isDirectory()){
                folderPos++;
                fileNames.add(folderPos, f);
                types.add(folderPos, "ПАПКА");
                createDates.add(folderPos, dc);
                modifiedDate.add(folderPos, dm);
            }
            if(f.isFile()){
                fileNames.add(f);
                types.add("Файл");
                createDates.add(dc);
                modifiedDate.add(dm);
            }
        }
        fireTableDataChanged();
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
