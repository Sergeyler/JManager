package fileutilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import javax.smartcardio.ATR;
import javax.swing.*;

//Класс, необходимый для получения свойств объектов
public class Properties {

    private static String name;         //Переменная для хранения имен объектов
    private static String type;         //Переменная для хранения типов объектов
    private static double size;           //Переменная для хранения размеров объектов
    private static Date dCreate;        //Переменная для хранения дат создания объектов
    private static Date dOpen;          //Переменная для хранения дат открытия объектов
    private static Date dModif;         //Переменная для хранения дат последней модификации объектов
    private static int countFiles;      //Переменная для хранения количества файлов
    private static int countFolders;    //Переменная для хранения количества папок

    public static void showProperties(File[] f){
        //Обнуляем содержимое числовых переменных
        size=0;
        countFiles=0;
        countFolders=0;

        //Действие, выполняемое когда массив объектов пуст
        if(f.length==0)return;

        //Элементы интерфейса для отображения свойств объектов
        JPanel msgPanel=new JPanel();
        msgPanel.setLayout(new BoxLayout(msgPanel, BoxLayout.Y_AXIS));
        JPanel[] rowPane=new JPanel[10];
        JLabel[] lSigns=new JLabel[10];        //Метки с наименованиями параметров
        JLabel[] lVals=new JLabel[10];         //Метки со значениями параметров

        //Действие, выполняемое, когда массив содержит один элемент
        if(f.length==1){

            //Проверяем, существует ли еще данный объект
            if(!f[0].exists()){
                JOptionPane.showMessageDialog(null, "<html>Невозможно получить свойства объекта.<br>Возможно он был перемещен или удален,<br>либо у Вас нет прав на доступ к нему.", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Вспомогательная переменная для доступа к атрибутам файлов и папок
            DosFileAttributes atr=null;

            //Если данный объект - файл
            if(f[0].isFile()){
                try {
                    name=f[0].getName();
                    type=getExtendFile(name);
                    size=f[0].length();
                    atr=Files.readAttributes(f[0].toPath(), DosFileAttributes.class);
                    dCreate=new Date(atr.creationTime().toMillis());
                    dOpen=new Date(atr.lastAccessTime().toMillis());
                    dModif=new Date(atr.lastModifiedTime().toMillis());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "<html>Невозможно получить свойства объекта.<br>Возможно он был перемещен или удален,<br>либо у Вас нет прав на доступ к нему.", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                DateFormat df=DateFormat.getInstance();
                NumberFormat nfLong=NumberFormat.getInstance();
                NumberFormat nfShort=NumberFormat.getInstance();
                nfShort.setMaximumFractionDigits(2);
                lSigns[0]=new JLabel("Имя:"); lVals[0]=new JLabel(name);
                lSigns[1]=new JLabel("Тип:"); lVals[1]=new JLabel(type);
                lSigns[2]=new JLabel("Размер:"); lVals[2]=new JLabel(nfLong.format(new Long((long)size))+" байт ( "+nfShort.format(size/(size>=1073741824?1073741824:1048576))+" "+(size>=1073741824?"Гб":"Мб")+")");
                lSigns[3]=new JLabel("Дата создания:"); lVals[3]=new JLabel(df.format(dCreate));
                lSigns[4]=new JLabel("Дата открытия:"); lVals[4]=new JLabel(df.format(dOpen));
                lSigns[5]=new JLabel("Дата изменения:"); lVals[5]=new JLabel(df.format(dModif));
                if(atr.isHidden()){
                    lSigns[6]=new JLabel("Файл скрыт"); lVals[6]=new JLabel("");
                }
                if(atr.isReadOnly()){
                    lSigns[7]=new JLabel("Файл только для чтения"); lVals[7]=new JLabel("");
                }
            }

            //Если данный объект - папка
            if(f[0].isDirectory()){
                try {
                    name=f[0].getName();
                    type="Папка";
                    //Следующая строка кода подсчитывает количество и общий размер файлов в папке и количество подпапок в ней
                    Files.walkFileTree(f[0].toPath(), new Walker(f[0]));
                    atr=Files.readAttributes(f[0].toPath(), DosFileAttributes.class);
                    dCreate=new Date(atr.creationTime().toMillis());
                    dOpen=new Date(atr.lastAccessTime().toMillis());
                    dModif=new Date(atr.lastModifiedTime().toMillis());

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "<html>Невозможно получить свойства объекта.<br>Возможно он был перемещен или удален,<br>либо у Вас нет прав на доступ к нему.", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                DateFormat df=DateFormat.getInstance();
                NumberFormat nfLong=NumberFormat.getInstance();
                NumberFormat nfShort=NumberFormat.getInstance();
                nfShort.setMaximumFractionDigits(2);
                lSigns[0]=new JLabel("Имя:"); lVals[0]=new JLabel(name);
                lSigns[1]=new JLabel("Тип:"); lVals[1]=new JLabel(type);
                lSigns[2]=new JLabel("Размер:"); lVals[2]=new JLabel(nfLong.format(new Long((long)size))+" байт ( "+nfShort.format(size/(size>=1073741824?1073741824:1048576))+" "+(size>=1073741824?"Гб":"Мб")+")");
                lSigns[3]=new JLabel("Дата создания:"); lVals[3]=new JLabel(df.format(dCreate));
                lSigns[4]=new JLabel("Дата открытия:"); lVals[4]=new JLabel(df.format(dOpen));
                lSigns[5]=new JLabel("Дата изменения:"); lVals[5]=new JLabel(df.format(dModif));
                lSigns[6]=new JLabel("Содержит файлов:"); lVals[6]=new JLabel(nfLong.format(new Long((long)countFiles)));
                lSigns[7]=new JLabel("Содержит папок:"); lVals[7]=new JLabel(nfLong.format(new Long((long)countFolders)));
                if(atr.isHidden()){
                    lSigns[8]=new JLabel("Папка скрыта"); lVals[8]=new JLabel("");
                }
                if(atr.isReadOnly()){
                    lSigns[9]=new JLabel("Папка только для чтения"); lVals[9]=new JLabel("");
                }
            }

        }

        //Действие, выполняемое, когда массив содержит группу элементов
        if(f.length>1){
            System.out.println("Данное действие еще не реализовано!");
        }

        //Выводим результат работы на экран
        for(int i=0;i<lSigns.length;i++){
            if(lSigns[i]==null)continue;
            rowPane[i]=new JPanel();
            rowPane[i].setLayout(new BoxLayout(rowPane[i], BoxLayout.X_AXIS));
            rowPane[i].setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
            lSigns[i].setHorizontalTextPosition(SwingConstants.LEFT);
            lVals[i].setHorizontalTextPosition(SwingConstants.RIGHT);
            rowPane[i].add(lSigns[i]);
            rowPane[i].add(Box.createHorizontalStrut(20));
            rowPane[i].add(Box.createHorizontalGlue());
            rowPane[i].add(lVals[i]);
            msgPanel.add(rowPane[i]);
        }
        JOptionPane.showMessageDialog(null, msgPanel, "Свойства файла", JOptionPane.INFORMATION_MESSAGE);

    }

    //Класс необходим для реализации обхода папок
    private static class Walker extends SimpleFileVisitor<Path>{

        private final Path root;

        Walker(File r){
            root=r.toPath();
        }

        @Override
        public FileVisitResult visitFile(Path f, BasicFileAttributes atr) throws IOException{
            size+=atr.size();
            countFiles++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path f, BasicFileAttributes atr) throws IOException{
            if(!f.equals(root))countFolders++;
            return FileVisitResult.CONTINUE;
        }

    }

    //Метод возвращает расширение файла nameFile. Если расширения нет - возвращает пустую строку.
    //Расширением считается последовательность символов после последней точки.
    private static String getExtendFile(String nameFile){
        String extendFile="";
        int dotPos=nameFile.lastIndexOf(".");
        if((dotPos==(-1)) | (dotPos==0) | (dotPos==nameFile.length()))extendFile=""; else extendFile=nameFile.substring(dotPos+1);
        return extendFile.toLowerCase();
    }

}
