/*
Michael Zhang
Calculator
ICS4U
2013/04/16
*/

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*; 

public class Calculator extends JFrame implements ActionListener {
	
	JTextField num1, num2, ans;
	JButton equals, clear, exit, op1, op2, op3, op4;
	String str1, str2;
	double dbl1, dbl2, dblAns;
	int defaultOP = 0;
	String[] operator = {"+","-","*","/"};
    
	public Calculator(){
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		num1 = new JTextField(10);
        c.add(num1);
        
        op1 = new JButton("+");
        c.add(op1);
        
        op2 = new JButton("-");
        c.add(op2);
        
        op3 = new JButton("*");
        c.add(op3);
        
        op4 = new JButton("/");
        c.add(op4);
        
        num2 = new JTextField(10);
        c.add(num2);
        
        equals = new JButton("=");
        c.add(equals);
        
        ans = new JTextField(15);
        ans.setEditable(false);
        c.add(ans);
		
        clear = new JButton("Clear");
        c.add(clear);
        
		exit = new JButton("Exit");
        c.add(exit);
        
        equals.addActionListener(this);
        clear.addActionListener(this);
        exit.addActionListener(this);
        op1.addActionListener(this);
        op2.addActionListener(this);
        op3.addActionListener(this);
        op4.addActionListener(this);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE);  
	}
  
	public void actionPerformed (ActionEvent e){

		if (e.getSource() == op1)
			defaultOP = 0;
		if (e.getSource() == op2)
			defaultOP = 1;
		if (e.getSource() == op3)
			defaultOP = 2;
		if (e.getSource() == op4)
			defaultOP = 3;
		
		if (e.getSource() == equals){
			str1 = num1.getText();
			str2 = num2.getText();
			dbl1 = Double.parseDouble(str1);
			dbl2 = Double.parseDouble(str2);
			if (defaultOP == 0)
				dblAns = dbl1 + dbl2;
			else if (defaultOP == 1)
				dblAns = dbl1 - dbl2;
			else if (defaultOP == 2)
				dblAns = dbl1*dbl2;
			else if (defaultOP == 3)
				dblAns = dbl1/dbl2;
			ans.setText(""+dblAns);
        }
		if (e.getSource() == clear){ 
			num1.setText("");
            num2.setText("");
            ans.setText("");
        }
		if (e.getSource() == exit){
			System.exit(0);
		}
	}
	public static void main(String args[]){
       
		Calculator window = new Calculator();
		window.setSize(380,150);
		window.show();

	}
}
