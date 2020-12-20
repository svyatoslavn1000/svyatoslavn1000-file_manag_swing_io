package fileManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class UI extends JFrame {
    private JPanel catalogPanel = new JPanel();
    private JList filesList = new JList();
    private JScrollPane filesScroll = new JScrollPane(filesList);
    private JPanel buttonsPanel = new JPanel();
    private JButton addFolder = new JButton("Создать директорию");
    private JButton backButton = new JButton("Назад");
    private JButton deleteButton = new JButton("Удалить");
    private JButton renameButton = new JButton("Переименовать");
    private ArrayList<String> dirscache = new ArrayList<>();

    public UI() {
        super("Проводник");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        catalogPanel.setLayout(new BorderLayout(5, 5));
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsPanel.setLayout(new GridLayout(1, 4, 5, 5));
        JDialog createNewDirDialog = new JDialog(UI.this, "Создание папки", true);
        JPanel createNewPanel = new JPanel();
        createNewDirDialog.add(createNewPanel);
        File discs[] = File.listRoots();
        filesScroll.setPreferredSize(new Dimension(400, 500));
        filesList.setListData(discs);
        filesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        filesList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultListModel model = new DefaultListModel();
                    String selectObject = filesList.getSelectedValue().toString();
                    String fullPath = toFullPath(dirscache);
                    File selectedFile;
                    if (dirscache.size() > 1) {
                        selectedFile = new File(fullPath, selectObject);
                    } else {
                        selectedFile = new File(fullPath + selectObject);
                    }

                    if (selectedFile.isDirectory()) {
                        String[] rootStr = selectedFile.list();
                        for (String str : rootStr) {
                            File checkObject = new File(selectedFile.getPath(), str);
                            if (!checkObject.isHidden()) {
                                if (checkObject.isDirectory()) {
                                    model.addElement(str);
                                } else {
                                    model.addElement("Файл" + str);
                                }
                            }
                        }
                        dirscache.add(selectObject);
                        filesList.setModel(model);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dirscache.size() > 1) {
                    dirscache.remove(dirscache.size() - 1);
                    String backDir = toFullPath(dirscache);
                    String[] object = new File(backDir).list();
                    DefaultListModel backRootModel = new DefaultListModel();

                    for (String str : object) {
                        File checkFile = new File(backDir, str);
                        if (!checkFile.isHidden()) {
                            if (checkFile.isDirectory()) {
                                backRootModel.addElement(str);
                            } else {
                                backRootModel.addElement("Файл" + str);
                            }
                        }
                    }
                    filesList.setModel(backRootModel);
                }
            }
        });

        addFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dirscache.isEmpty()) {
                    String currentPath;
                    File newFolder;

                    CreateNewFolderJDialog newFolderDialog = new CreateNewFolderJDialog(UI.this);

                    if (newFolderDialog.isReady()) {
                        currentPath = toFullPath(dirscache);
                        newFolder = new File(currentPath, newFolderDialog.getNewFolderName());
                        if (!newFolder.exists()) {
                            newFolder.mkdir();
                        }

                        File updateDir = new File(currentPath);
                        String updateMas[] = updateDir.list();
                        DefaultListModel updateModel = new DefaultListModel();
                        for (String str : updateMas) {
                            File check = new File(updateDir.getPath(), str);
                            if (!check.isHidden()) {
                                if (check.isDirectory()) {
                                    updateModel.addElement(str);
                                } else {
                                    updateModel.addElement("файл" + str);
                                }
                            }
                        }
                        filesList.setModel(updateModel);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedObject = filesList.getSelectedValue().toString();
                String currentPath = toFullPath(dirscache);
                if (!selectedObject.isEmpty()) {

                    deleteDir(new File(currentPath, selectedObject));

                    File updateDir = new File(currentPath);
                    String updateMas[] = updateDir.list();
                    DefaultListModel updateModel = new DefaultListModel();

                    for (String str : updateMas) {
                        File check = new File(updateDir.getPath(), str);
                        if (!check.isHidden()) {
                            if (check.isDirectory()) {
                                updateModel.addElement(str);
                            } else {
                                updateModel.addElement("файл" + str);
                            }
                        }
                    }
                    filesList.setModel(updateModel);
                }
            }
        });

        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedObject = filesList.getSelectedValue().toString();
                String currentPath = toFullPath(dirscache);
                if (!dirscache.isEmpty() & filesList.getSelectedValue() != null) {


                    RenameJDIalog renamer = new RenameJDIalog(UI.this);

                    if (renamer.isReady()) {
                        File renameFile = new File(currentPath, selectedObject);
                        renameFile.renameTo(new File(currentPath, renamer.getNewFolderName()));

                        File updateDir = new File(currentPath);
                        String updateMas[] = updateDir.list();
                        DefaultListModel updateModel = new DefaultListModel();
                        for (String str : updateMas) {
                            File check = new File(updateDir.getPath(), str);
                            if (!check.isHidden()) {
                                if (check.isDirectory()) {
                                    updateModel.addElement(str);
                                } else {
                                    updateModel.addElement("файл" + str);
                                }
                            }
                        }
                        filesList.setModel(updateModel);

                    }
                }
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(addFolder);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(renameButton);
        catalogPanel.setLayout(new BorderLayout());
        catalogPanel.add(filesScroll, BorderLayout.CENTER);
        catalogPanel.add(buttonsPanel, BorderLayout.SOUTH);

        getContentPane().add(catalogPanel);
        setSize(600, 600);
        //pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public String toFullPath(ArrayList<String> file) {
        String listPart = "";
        for (String str : file) {
            listPart = listPart + str;
        }
        return listPart;
    }

    public static void deleteDir(File file) {
        File[] objects = file.listFiles();
        if (objects != null) {
            for (File f : objects) {
                deleteDir(f);
            }
        }
        file.delete();
    }


}
