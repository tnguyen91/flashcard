import java.util.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class FlashCardPlayer{
    private JTextArea display;
    private JButton showDefinition, nextButton, previousButton, practice, back;
    private boolean isShowingDefinition;
    private FlashCard currentCard, card1, card2;
    private JFrame frame;
    private ArrayList<FlashCard> cardList;
    private ListIterator<FlashCard> cardIterator;

    public FlashCardPlayer(File file) {
        
        frame = new JFrame(file.getName().replaceFirst(".txt", ""));
        JPanel panel = new JPanel();
        Font font = new Font("sanserif", Font.BOLD, 20);

        display = new JTextArea(6, 16);
        display.setFont(font);
        display.setLineWrap(true);
        display.setEditable(false);

        JScrollPane tScroller = new JScrollPane(display);
        tScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        isShowingDefinition = false;
        
        showDefinition = new JButton("Show definition");
        
        showDefinition.setPreferredSize(new Dimension(122, 30));
        showDefinition.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isShowingDefinition == false){
                    showDefinition.setText("Show term");
                    display.setText(currentCard.getDefinition());
                    isShowingDefinition = true;
                }
                else{
                    showDefinition.setText("Show definition");  
                    display.setText(currentCard.getTerm());
                    isShowingDefinition = false;
                }
            }
        });
        
        loadFile(file);

        previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cardIterator.hasPrevious()){
                    showPreviousCard();
                    
                    isShowingDefinition = false;
                    
                }
              
            }
        });

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cardIterator.hasNext()){
                    showNextCard();
                    isShowingDefinition = false;
                    
                }
            }
        });

        practice = new JButton("Practice");
        practice.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Practice(cardList, frame);
                frame.setVisible(false);
            }
        });

        back = new JButton("Go Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Start();
				frame.dispose();
			}
		});

        panel.add(tScroller); 
        panel.add(showDefinition);
        panel.add(previousButton);
        panel.add(nextButton);
        panel.add(practice);
        panel.add(back);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(440, 500);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    private void showPreviousCard() {
        currentCard = (FlashCard) cardIterator.previous();
        card1 = currentCard;
        if(card1.equals(card2) && cardIterator.hasPrevious()){
            currentCard = (FlashCard) cardIterator.previous();
            card1 = currentCard;
        }
        display.setText(currentCard.getTerm());
        showDefinition.setText("Show definition");
    }

    private void showNextCard() {
        currentCard = (FlashCard) cardIterator.next();
        card2 = currentCard;
        if(card1 == null){
        }
        else if(card2 == card1){
            currentCard = (FlashCard) cardIterator.next();
        }
        display.setText(currentCard.getTerm());
        showDefinition.setText("Show definition");
    }

    private void loadFile(File selectedFile) {
        cardList = new ArrayList<FlashCard>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;
            while((line = reader.readLine()) != null){
                createCard(line);
            }
            reader.close();
        } catch (Exception e) {}
        cardIterator = cardList.listIterator();
        showNextCard();
    }

    private void createCard(String line) {
        String[] result = line.split("/");
        FlashCard card = new FlashCard(result[0], result[1]);
        cardList.add(card);
    }
}