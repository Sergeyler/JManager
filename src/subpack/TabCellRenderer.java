package subpack;

import java.awt.*;
import javax.swing.*;
import java.io.*;

import javax.swing.table.DefaultTableCellRenderer;

public class TabCellRenderer extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable tab, Object v, boolean selected, boolean focus, int r, int c){
        JLabel l=(JLabel)(super.getTableCellRendererComponent(tab, v, selected, focus, r, c));
        if(v instanceof File){
            File f=(File)v;
            if(f.isDirectory()){
                l.setIcon(new ImageIcon("icons\\folder.png"));
            } else l.setIcon(new ImageIcon(""));
        }else l.setIcon(new ImageIcon(""));
        return l;
    }

}
