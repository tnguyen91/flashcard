import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FlashCardBuilder{
	private JTextArea subject;
	private JTextArea term, definition;
	private JLabel subjectLabel, termLabel, definitionLabel, savedLabel;
	private JButton saveCard, done, back;
	private ArrayList<FlashCard> cardList;
	private JFrame frame;
	
	public FlashCardBuilder(){
		frame = new JFrame("Create study set");
		JPanel panel = new JPanel();
		Font font = new Font("sanserif", Font.BOLD, 20);
		
		subject = new JTextArea(1, 21);
		subject.setLineWrap(true);
		subject.setWrapStyleWord(true);
		subject.setFont(font);

		term = new JTextArea(6, 20);
		term.setLineWrap(true);
		term.setWrapStyleWord(true);
		term.setFont(font);
		
		JScrollPane tScroller = new JScrollPane(term);
		tScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		definition = new JTextArea(6,20);
		definition.setLineWrap(true);
		definition.setWrapStyleWord(true);
		definition.setFont(font);
		
		JScrollPane dScroller = new JScrollPane(definition);
		dScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		dScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		cardList = new ArrayList<FlashCard>();

		saveCard = new JButton("Save Card");
		saveCard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				String t = term.getText().trim();
				String d = definition.getText().trim();
  				if(t.equals("") && d.equals("")) {
					savedLabel.setText("You did not create any card!");
    			}
				else{
					FlashCard card = new FlashCard(term.getText(), definition.getText());
					cardList.add(card);
					term.setText("");
					definition.setText("");
					savedLabel.setText("");
					term.requestFocus();
				}
				
			}
		});

		done = new JButton("Done");
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (cardList.isEmpty() == false){
					try {
						File file = new File(subject.getText() + ".txt");
						file.createNewFile();
						PrintWriter writer = new PrintWriter(new FileWriter(file, true));
						for (FlashCard card: cardList){
							writer.write(card.getTerm() + "/");
							writer.write(card.getDefinition() + "\n");
						}
						writer.close();
						subject.setText("");
						term.setText("");
						definition.setText("");
						savedLabel.setText("Saved");
						cardList.clear();
					} catch (Exception a) {}
				}
				else{
					savedLabel.setText("You did not create any card!");
				}
			}
		});

		back = new JButton("Go Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Start();
				frame.dispose();
			}
		});
		
		subjectLabel = new JLabel("Subject");
		subjectLabel.setPreferredSize(new Dimension(58,30));

		termLabel = new JLabel("Term");
		termLabel.setPreferredSize(new Dimension(58,30));

		definitionLabel = new JLabel("Definition");
		definitionLabel.setPreferredSize(new Dimension(58,30));

		savedLabel = new JLabel();
		savedLabel.setPreferredSize(new Dimension(200, 30));

		panel.add(subjectLabel);
		panel.add(subject);
		panel.add(termLabel);
		panel.add(tScroller);
		panel.add(definitionLabel);
		panel.add(dScroller);
		panel.add(saveCard);
		panel.add(done);
		panel.add(back);
		panel.add(savedLabel);

		
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(440, 500);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}