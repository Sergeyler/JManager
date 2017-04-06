package jmanager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import java.io.*;
import subpack.*;

public class FilePane {

    private final JFrame frame;
    private int W=1000;
    private int H=800;

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

    //Метка для отображения дополнительной информации
    JTextField fullFolderPath=new JTextField();

    //Чек бокс для выбора отображения скрытых файлов и папок
    private JCheckBox showHiddenElements=new JCheckBox("Показывать скрытые элементы", true);

    //Текущая папка, содержимое которой отображается в tab
    private File folder=new File(System.getProperty("user.home"));
    private boolean hiddenEnabled=true;

    public FilePane() {
        //Предварительные действия по формированию окна программы
        String laf=UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            JOptionPane.showMessageDialog(null, "Возникла ошибка при попытке переключить стиль интерфейса. Работа программы будет прекращена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        frame=new JFrame("JManager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(W, H);
        frame.setIconImage(new ImageIcon("icons\\logo.png").getImage());
        frame.setResizable(false);
        frame.setLayout(new BorderLayout(10, 10));
        int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-W/2;
        int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-H/2;
        frame.setLocation(xPos, yPos);

        //Создаем северную панель
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        upButton=new JButton(new ImageIcon("icons\\up.png"));
        upButton.setToolTipText("Вверх");
        northPanel.add(upButton);
        northPanel.add(Box.createHorizontalStrut(10));
        northPanel.add(new JLabel("Диск:"));
        northPanel.add(Box.createHorizontalStrut(10));
        diskList=new JComboBox<>();
        diskList.setToolTipText("Сменить диск");
        refreshDiskList();
        northPanel.add(diskList);
        northPanel.add(Box.createHorizontalGlue());
        refreshButton=new JButton(new ImageIcon("icons\\refresh.png"));
        refreshButton.setToolTipText("Обновить");
        northPanel.add(refreshButton);
        contentPanel.add(northPanel, BorderLayout.NORTH);

        //Создаем центральную панель с таблицей, представляющей текущий каталог
        tm=new TabModel(folder, hiddenEnabled);
        tcr=new TabCellRenderer();
        tab=new JTable(tm);
        tab.setDefaultRenderer(Object.class, tcr);
        tab.setRowHeight(19);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp=new JScrollPane(tab);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tab.setShowVerticalLines(false);
        tab.setGridColor(new Color(220,220,200));
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        centerPanel.add(sp, BorderLayout.CENTER);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        //Создаем южную панель
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        fullFolderPath.setEditable(false);
        fullFolderPath.setText(folder.getAbsolutePath());
        southPanel.add(fullFolderPath);
        southPanel.add(showHiddenElements);
        showHiddenElements.setHorizontalTextPosition(SwingConstants.LEFT);
        contentPanel.add(southPanel, BorderLayout.SOUTH);

        //Создаем обработчики событий
        //Обработчик кпоки "Вверх"
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

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        //Устанавливаем ширину столбцов в зависимости от ширины содержащего таблитцу контенера
        tab.getColumnModel().getColumn(0).setPreferredWidth((int)(sp.getWidth()*0.52));
        tab.getColumnModel().getColumn(1).setPreferredWidth((int)(sp.getWidth()*0.1));
        tab.getColumnModel().getColumn(2).setPreferredWidth((int)(sp.getWidth()*0.1));
        tab.getColumnModel().getColumn(3).setPreferredWidth((int)(sp.getWidth()*0.13));
        tab.getColumnModel().getColumn(4).setPreferredWidth((int)(sp.getWidth()*0.13));

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{FilePane m=new FilePane();});
    }

}
