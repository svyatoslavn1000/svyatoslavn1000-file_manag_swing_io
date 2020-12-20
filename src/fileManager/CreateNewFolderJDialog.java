package fileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateNewFolderJDialog extends JDialog {
    private JTextField nameOfNewFolder = new JTextField(10);
    private JButton okButton = new JButton("Создать");
    private JButton canselButton = new JButton("Отмена");
    private String newFolderName;
    private JLabel nameFolderWait = new JLabel("Имя новой папки");
    private boolean ready = false;

    public CreateNewFolderJDialog(JFrame jFrame) {

        super(jFrame, "Создать новую папку", true);
        setLayout(new GridLayout(2, 2, 5, 5));
        setSize(400, 200);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFolderName = nameOfNewFolder.getText();
                setVisible(false);
                ready = true;
            }
        });

        canselButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ready = false;
            }
        });

        getContentPane().add(nameFolderWait);
        getContentPane().add(nameOfNewFolder);
        getContentPane().add(okButton);
        getContentPane().add(canselButton);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public boolean isReady() {
        return ready;
    }
}
