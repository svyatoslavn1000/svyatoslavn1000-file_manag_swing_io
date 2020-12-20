package fileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameJDIalog extends JDialog {
    private JTextField nameOfNewFolder = new JTextField();
    private JButton okButton = new JButton("Переименовать");
    private JButton cancelButton = new JButton("Отмена");
    private String newFolderName;
    private JLabel nameFolderWait = new JLabel("Новое имя:");
    private boolean ready = false;

    public String getNewFolderName() {
        return newFolderName;
    }

    public boolean isReady() {
        return ready;
    }

    public RenameJDIalog(JFrame jFrame) {
        super(jFrame, "Переименовать", true);
        setLayout(new GridLayout(2, 2, 5, 5));
        setSize(400,200);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFolderName = nameOfNewFolder.getText();
                setVisible(false);
                ready = true;
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ready = false;
            }
        });
        getContentPane().add(nameFolderWait);
        getContentPane().add(nameOfNewFolder);
        getContentPane().add(okButton);
        getContentPane().add(cancelButton);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
