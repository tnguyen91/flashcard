import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Practice{
    
    private JTextArea display, answer;
    private JLabel notify;
    private JButton submit, help, back;
    private boolean isCorrect, lastCard, hasNext;
    private FlashCard currentCard;
    private ListIterator<FlashCard> cardIterator;
    private JFrame frame;
    
    public Practice(ArrayList<FlashCard> cardList, JFrame frame2) {
        frame = new JFrame("Practice");
        JPanel panel = new JPanel();
        Font font = new Font("sanserif", Font.BOLD, 20);
        
        cardIterator = cardList.listIterator();

        display = new JTextArea(6,19);
        display.setFont(font);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setEditable(false);
        
        JScrollPane dScroller = new JScrollPane(display);
		dScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		dScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        nextCard();

        notify = new JLabel();
        notify.setPreferredSize(new Dimension(250,50));
        notify.setFont(font);
        
        answer = new JTextArea(1, 20);
        answer.setFont(font);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        
        submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                isCorrect = answer.getText().equals(currentCard.getTerm());
                if (hasNext == false && isCorrect){
                    notify.setText("That was the last card!");
                    display.setText("");
                    answer.setText("");
                    lastCard = true;
                }
                else if (isCorrect){
                    nextCard();
                    notify.setText("");
                    answer.setText("");
                    answer.requestFocus();
                }
                else if (lastCard){}
                else{
                    notify.setText("\nIncorrect!");
                    answer.setText("");
                    answer.requestFocus();
                }
            }
        });

        help = new JButton("Don't know");
        help.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (lastCard == false){
                    notify.setText(currentCard.getTerm());
                    answer.requestFocus();
                }
            }
        });

        back = new JButton("Go Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                frame2.setVisible(true);
                frame.dispose();
			}
		});

        panel.add(dScroller);
        panel.add(notify);
        panel.add(answer);
        panel.add(submit);
        panel.add(help);
        panel.add(back);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(440, 500);
        frame.setResizable(false);
        frame.setVisible(true);
	}

    public void nextCard(){
        if (cardIterator.hasNext()){
            currentCard = (FlashCard) cardIterator.next();
            display.setText(currentCard.getDefinition());
            lastCard = false;
            hasNext = true;
        }
        if (cardIterator.hasNext() == false){
            hasNext = false;
        }
    }
}
