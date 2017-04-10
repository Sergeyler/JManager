package jmanager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.io.*;
import subpack.*;

public class FilePane {

    //Панели с контентом
    private JPanel contentPanel=new JPanel(new BorderLayout());
    private JPanel northPanel=new JPanel();
    private JPanel centerPanel=new JPanel(new BorderLayout());
    private JPanel southPanel=new JPanel();

    //Компонент для отображения списка быстрого доступа к дискам
    private JComboBox<String> diskList;

    //Кнопка "Вверх"
    private final JButton upButton;

    //Кнопка "Обновить"
    private final JButton refreshButton;

    //Таблица для отображения содержимого папки и модель данных для нее
    private JTable tab;
    private TabModel tm;
    private TabCellRenderer tcr;
    private TabHeaderRenderer thc;

    //Метка для отображения дополнительной информации
    JTextField fullFolderPath=new JTextField();

    //Текущая папка, содержимое которой отображается в tab
    private File folder=new File(System.getProperty("user.home"));
    private boolean hiddenEnabled=false;

    //Чек бокс для выбора отображения скрытых файлов и папок
    private JCheckBox showHiddenElements=new JCheckBox("Показывать скрытые элементы", hiddenEnabled);

    public FilePane(int W, int H) {
        //Создаем панель контента
        contentPanel.setPreferredSize(new Dimension(W, H));

        //Создаем северную панель
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        upButton=new JButton(new ImageIcon("icons\\up_blue.png"));
        upButton.setToolTipText("Вверх");
        northPanel.add(upButton);
        northPanel.add(Box.createHorizontalStrut(10));
        northPanel.add(new JLabel("Диск:"));
        northPanel.add(Box.createHorizontalStrut(5));
        diskList=new JComboBox<>();
        diskList.setMaximumSize(new Dimension(30, 24));
        diskList.setToolTipText("Сменить диск");
        refreshDiskList();
        northPanel.add(diskList);
        northPanel.add(Box.createHorizontalGlue());
        refreshButton=new JButton(new ImageIcon("icons\\refresh_blue.png"));
        refreshButton.setToolTipText("Обновить");
        northPanel.add(refreshButton);
        contentPanel.add(northPanel, BorderLayout.NORTH);

        //Создаем центральную панель с таблицей, представляющей текущий каталог
        tm=new TabModel(folder, hiddenEnabled);
        tcr=new TabCellRenderer();
        thc=new TabHeaderRenderer();
        tab=new JTable(tm);
        tab.setDefaultRenderer(Object.class, tcr);
        tab.getTableHeader().setDefaultRenderer(thc);
        tab.setRowHeight(19);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp=new JScrollPane(tab);
        tab.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tab.setShowVerticalLines(false);
        tab.setGridColor(new Color(220,220,200));
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        centerPanel.add(sp, BorderLayout.CENTER);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        //Устанавливаем ширину столбцов в зависимости от ширины содержащего таблитцу контейнера
        tab.getColumnModel().getColumn(0).setPreferredWidth((int)(contentPanel.getPreferredSize().getWidth()*0.52));
        tab.getColumnModel().getColumn(1).setPreferredWidth((int)(contentPanel.getPreferredSize().getWidth()*0.1));
        tab.getColumnModel().getColumn(2).setPreferredWidth((int)(contentPanel.getPreferredSize().getWidth()*0.1));
        tab.getColumnModel().getColumn(3).setPreferredWidth((int)(contentPanel.getPreferredSize().getWidth()*0.13));
        tab.getColumnModel().getColumn(4).setPreferredWidth((int)(contentPanel.getPreferredSize().getWidth()*0.13));

        //Создаем южную панель
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
        fullFolderPath.setEditable(false);
        fullFolderPath.setText(folder.getAbsolutePath());
        southPanel.add(fullFolderPath);
        southPanel.add(showHiddenElements);
        showHiddenElements.setHorizontalTextPosition(SwingConstants.LEFT);
        contentPanel.add(southPanel, BorderLayout.SOUTH);

        //Создаем обработчики событий
        //Обработчик кнопки "Вверх"
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File nextFolder=folder.getParentFile();
                if(nextFolder==null)nextFolder=folder.toPath().getRoot().toFile();
                folder=nextFolder;
                fullFolderPath.setText(folder.getAbsolutePath());
                tm.refresh(folder, hiddenEnabled);
            }
        });

        //Обработчик смены текущего диска
        diskList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(diskList.getSelectedItem()==null)return;
                File selDisk=new File(diskList.getSelectedItem().toString());
                if(selDisk.exists() & selDisk.canRead()){
                    folder=selDisk;
                    fullFolderPath.setText(folder.getAbsolutePath());
                    tm.refresh(folder, hiddenEnabled);
                    return;
                }
                JOptionPane.showMessageDialog(contentPanel, "Диск "+diskList.getSelectedItem().toString()+" не доступен для чтения.", "Ошибка доступа к диску", JOptionPane.ERROR_MESSAGE);
                refreshDiskList();
            }
        });

        //Обработчик кнопки "Обновить"
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDiskList();
                tm.refresh(folder, hiddenEnabled);
            }
        });

        //Обработчик выбора режима отображения скрытых элементов
        showHiddenElements.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                hiddenEnabled=!hiddenEnabled;
                tm.refresh(folder, hiddenEnabled);
            }
        });

        //Обработчик щелчка на столбце
        tab.getTableHeader().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getButton()!=1)return;
                if(e.getClickCount()!=1)return;
                tm.sort(tab.columnAtPoint(new Point(e.getX(), e.getY())));

                //Вызов этого метода необходим для корректной прорисовки заголовков столбцов
                tab.getTableHeader().repaint();
            }
        });

        //Обработчик щелчка левой кнопкой мыши на строке
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getButton()!=1)return;
                if(e.getClickCount()!=2)return;
                File f=(File)tm.getValueAt(tab.getSelectedRow(), 0);

                //Проверяем существование и доступность выбранного элемента
                if(!f.exists() | !f.canRead()){
                    JOptionPane.showMessageDialog(contentPanel, f.getName()+" не существует или недоступен для чтения", "Нет доступа к элементу", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                //Пытаемся открыть элемент
                //Если это папка
                if(f.isDirectory()){
                    folder=f;
                    tm.refresh(folder, hiddenEnabled);
                    fullFolderPath.setText(folder.getAbsolutePath());
                    return;
                }

                //Если это файл
                if(f.isFile()){
                    try {
                        Desktop.getDesktop().open(f);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(contentPanel, "Не удалось открыть файл "+f.getName(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }

    //Метод обновляет список доступных дисков
    private void refreshDiskList(){
        File[] roots=File.listRoots();
        DefaultComboBoxModel<String> cbm=new DefaultComboBoxModel<>();
        for(File f: roots)if(f.exists() & f.canRead())cbm.addElement(f.toString());
        for (File root : roots) {
            if (root.toString().equals(folder.toPath().getRoot().toString())) {
                cbm.setSelectedItem(root);
                break;
            }
        }
        diskList.setModel(cbm);
    }

    //Метод возвращает панель с контентом
    public JPanel getContentPanel(){
        return contentPanel;
    }

}
