import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Start{
    private JTextArea search;
    private JTextPane displaySet;
    private JButton find, createSet, showAllSet;
    private JFrame frame;
    private JLabel[] set;
    private ArrayList<String> studySet;

    public Start(){
        frame = new JFrame("Flash Card");
        JPanel panel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("sanserif", Font.BOLD, 20);

        search = new JTextArea(1, 10);
        search.setFont(font);

        find = new JButton("Find");
        find.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    displaySet.setText("Sets contain \"" + search.getText() +"\"\n");
                    makeListOfSet();
                    ArrayList<String> studySet2 = new ArrayList<>();
                    for (String s: studySet){
                        File f = new File("./" + s);
                        BufferedReader reader = new BufferedReader(new FileReader(f));
                        String line = null;
                        while((line = reader.readLine()) != null){
                            String[] result = line.split("/|\\ ");
                            List<String> list = Arrays.asList(result);
                            if(list.contains(search.getText())){
                                studySet2.add(s);
                            }
                        }
                        reader.close();
                    }
                    set = new JLabel[studySet2.size()];
                    MouseListener ml = new MouseListener(){
                        public void mouseClicked(MouseEvent e){
                            JLabel label = (JLabel) e.getSource();
                            File file = new File("./" + label.getText() + ".txt");
                            new FlashCardPlayer(file);
                            frame.setVisible(false);
                        }
                        public void mousePressed(java.awt.event.MouseEvent e) {}
                        public void mouseReleased(java.awt.event.MouseEvent e) {}
                        public void mouseEntered(java.awt.event.MouseEvent e) {}
                        public void mouseExited(java.awt.event.MouseEvent e) {}
                    };

                    for (int i = 0; i < set.length; i++){
                        set[i] = new JLabel(studySet2.get(i).replaceFirst(".txt", ""));
                        set[i].setPreferredSize(new Dimension(290, 20));
                        set[i].addMouseListener(ml);
                        displaySet.insertComponent(set[i]);
                    }
                } catch (Exception ex) {}
            }
        });

        showAllSet = new JButton("Show all study set");
        showAllSet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    displaySet.setText("All sets\n");
                    makeListOfSet();
                    set = new JLabel[studySet.size()];
                    MouseListener ml = new MouseListener(){
                        public void mouseClicked(MouseEvent e){
                            JLabel label = (JLabel) e.getSource();
                            try {
                                File file = new File("./" + label.getText() + ".txt");
                                new FlashCardPlayer(file);
                                frame.setVisible(false);
                            } catch (Exception ex) {}
                        }
                        public void mousePressed(java.awt.event.MouseEvent e) {}
                        public void mouseReleased(java.awt.event.MouseEvent e) {}
                        public void mouseEntered(java.awt.event.MouseEvent e) {}
                        public void mouseExited(java.awt.event.MouseEvent e) {}
                    };

                    for (int i = 0; i < set.length; i++){
                        set[i] = new JLabel(studySet.get(i).replaceFirst(".txt", ""));
                        set[i].setPreferredSize(new Dimension(290, 20));
                        set[i].addMouseListener(ml);
                        displaySet.insertComponent(set[i]);         
                    }
                } catch (Exception ex) {}
            }
        });

        createSet = new JButton("Create a new study set"); 
        createSet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new FlashCardBuilder();
                frame.setVisible(false);
            }
        });

        displaySet = new JTextPane();
        displaySet.setPreferredSize(new Dimension(290, 365));
        displaySet.setEditable(false);

        panel.add(search);
        panel.add(find);
        panel.add(showAllSet);
        panel.add(createSet);
        panel.add(displaySet);
        
        frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(300, 500);
        frame.setResizable(false);
		frame.setVisible(true);
    }

    private void makeListOfSet(){
        try {
            studySet = new ArrayList<String>();
            File f = new File(".");
            FilenameFilter textFilter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".txt");
                }
            };
            File[] files = f.listFiles(textFilter);
            for (File file: files) {
                String str = file.getName();
                studySet.add(str);
            }
        } catch (Exception e) {}
        
    }

    public static void main(String[] args){
		new Start();
	}
}
