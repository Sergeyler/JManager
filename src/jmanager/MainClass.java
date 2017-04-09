package jmanager;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.*;

public class MainClass {

    private final JFrame frame;

    private final int W=1400;
    private final int H=850;

    private final JPanel northPanel=new JPanel();

    private final JButton properties_left=new JButton("Свойства");
    private final JButton allot_left=new JButton("Выделить все");
    private final JButton rename_left=new JButton("Переименовать");
    private final JButton del_left=new JButton("Удалить");
    private final JButton move_left=new JButton("Переместить");
    private final JButton copy_left=new JButton("Копировать");

    private final JButton properties_right=new JButton("Свойства");
    private final JButton allot_right=new JButton("Выделить все");
    private final JButton rename_right=new JButton("Переименовать");
    private final JButton del_right=new JButton("Удалить");
    private final JButton move_right=new JButton("Переместить");
    private final JButton copy_right=new JButton("Копировать");

    private final JPanel centerPanel=new JPanel();
    private final JPanel left;
    private final JPanel right;

    public MainClass() {
        //Предварительные действия по формированию окна программы
//        String laf=UIManager.getSystemLookAndFeelClassName();
//        try {
//            UIManager.setLookAndFeel(laf);
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
//            JOptionPane.showMessageDialog(null, "Возникла ошибка при попытке переключить стиль интерфейса. Работа программы будет прекращена", "Ошибка", JOptionPane.ERROR_MESSAGE);
//            System.exit(0);
//        }
        frame=new JFrame("JManager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(W, H);
        frame.setIconImage(new ImageIcon("icons\\logo.png").getImage());
        frame.setResizable(false);
        frame.setLayout(new BorderLayout(10, 10));
        int xPos=Toolkit.getDefaultToolkit().getScreenSize().width/2-W/2;
        int yPos=Toolkit.getDefaultToolkit().getScreenSize().height/2-H/2;
        frame.setLocation(xPos, yPos);

        //Формируем северную панель
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        northPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        northPanel.add(properties_left);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(allot_left);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(rename_left);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(del_left);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(move_left);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(copy_left);

        northPanel.add(Box.createHorizontalGlue());

        northPanel.add(properties_right);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(allot_right);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(rename_right);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(del_right);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(move_right);
        northPanel.add(Box.createHorizontalStrut(3));
        northPanel.add(copy_right);

        //Формируем центральную панель
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        left=new FilePane((int)(W/2), H).getContentPanel();
        right=new FilePane((int)(W/2), H).getContentPanel();
        centerPanel.add(left);
        centerPanel.add(right);

        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{MainClass m=new MainClass();});
    }

}
