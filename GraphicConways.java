/**
Program to demonstrate conway's game of life using GUI
This program takes input from the user about number of rows and columns
@author : Sahitya Pavurala
//ID : 0490373
//HW 06
*/


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
	Create a frame which contains button panel which holds buttons 
	and gamepanel which holds the grid 
 */
class MainWindow extends JFrame
{   
	/**rows is a member variable which is of type int*/
	private int rows;
	/**columns is a member variable which is of type int*/
	private int columns;
	/**buttonPanel is a member variable which is of type JPanel*/
	private JPanel buttonPanel;
	/**gamePanel is a member variable which is of type JPanel*/
	private JPanel gamePanel;
	/**cells is a member variable which is of type CellPanel[][]*/
	private CellPanel[][] cells;
	
	/** MainWindow(int rows, int columns) is a constructor which constructs the MainWindow
	 @param	 rows 	number of rows in the grid, provided by the user
			 columns 	number of columns in the grid, provided by the user 	
	 */
	public MainWindow(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		//title for the frame
		setTitle("CONWAY'S GAME OF LIFE");
		
		//creating new Jpanel to create grid, should be final because we are accesing it in the inner class
		final JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(rows,columns,1,1));
		//creating array of cell panels
		cells = new CellPanel[rows][columns];
		for (int i =0 ; i < rows ; i++)
		{
			for(int j = 0;j< columns ; j++)
			{
				cells[i][j] = new CellPanel();
				gamePanel.add(cells[i][j]);
			}
		}
		//images for the reset ans exit buttons
		ImageIcon resetIcon = new ImageIcon("img.png");
		ImageIcon exitIcon = new ImageIcon("img2.png");
		
		//Panel to hold the buttons
		buttonPanel = new JPanel(new GridLayout(0,1,0,0));
		JLabel nextlabel = new JLabel("Click this button for next generation ==>>");
		JButton nextGeneration = new JButton("Next Generation");
		//nextGeneration.setSize(5,5);
		//Functionality for the nextGeneration button
		nextGeneration.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
			{
                nextGenerationPanel();
		
            }
        });
		
		JLabel resetlabel = new JLabel("Click this button to reset ==>>");
		JButton reset = new JButton(resetIcon);
		//Functionality for the reset button
		reset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for ( CellPanel[]  row : cells)
				{
					for(CellPanel cell : row)
					{
						cell.setBackground(Color.BLUE);
					}
				}	
			}
		});
		
		JLabel exitlabel = new JLabel("Click this button to exit ==>>");
		JButton exit = new JButton(exitIcon);
		////Functionality for the exit button
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		//adding all the buttons and labels
		buttonPanel.add(nextlabel);
		buttonPanel.add(nextGeneration);
		buttonPanel.add(resetlabel);
		buttonPanel.add(reset);
		buttonPanel.add(exitlabel);
		buttonPanel.add(exit);
		buttonPanel.setBorder( new TitledBorder("Play Here") );
		
		//nextGeneration.setRolloverIcon(aliveIcon);
		//adding all panels to the frame
		add(buttonPanel , BorderLayout.WEST);	
		add(gamePanel);
		pack();
		
	}	
	
	/** nextGenerationPanel() is a method for getting the next generation panel
		it is called when the next generation button is clicked
	*/
	void nextGenerationPanel()
	{	
		//matchArray is used to check the status of cells in the nextGenerations method
		//it holds boolean values, true if the cell color is black and false if the cell color is blue
		boolean[][] matchArray = new boolean[rows][columns];
		for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(cells[i][j].getBackground() == Color.BLACK) {
                    matchArray[i][j] = true;
                }
				else
					matchArray[i][j] = false;
				
            }
        }
		//nextGenerationArray holds the return value of the nextGenerations method
		//the member cells is modified based on this
		boolean[][] nextGenerationArray = nextGenerations(matchArray);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(nextGenerationArray[i][j] == true) {
                    cells[i][j].setBackground(Color.BLACK);
                }
                else {
                    cells[i][j].setBackground(Color.BLUE);
                }
            }
        }
    }
	
	/** nextGenerations(boolean[][] matchArray) is a method for calculating the next generation
	@param		matchArray		it is the boolean array constructed in the nextGenerationPanel() method
	@return		nextGenerationArray		it is the boolean array based on which the member cells is modified
	*/	
	boolean[][] nextGenerations(boolean[][] matchArray)
	{	
		//nextGenerationArray holds the status of cell for the next generation
		boolean[][] nextGenerationArray = new boolean[rows][columns];
		for(int i = 0;i < rows ; i++)
		{
			for(int j = 0; j < columns ; j++)
			{	int neighbours = 0 ;// holds the number of neighbours of each cell
				for(int k =i-1; k < i+2 ;k++)
				{
					for(int l=j-1; l < j+2 ; l++)
					{
						if(k < 0 || k >= rows || l < 0 || l >= columns){continue;}
						else if( k == i && l == j ){continue;}
						else if ( matchArray[k][l] == true)
							neighbours  += 1;
					}
				}
				//checks if the cell lives in the next generation
				if (matchArray[i][j] == true &&( neighbours == 2 || neighbours == 3))
					{nextGenerationArray[i][j] = true;}
				//checks if the cell is a birth cell	
				else if (matchArray[i][j] == false && neighbours == 3) 
				    nextGenerationArray[i][j] = true;
				else
					nextGenerationArray[i][j]= false;
			}
		}
	return nextGenerationArray;
	}
}

/**
	Create cells which are placed in the grid
	the cell which is alive is represented in black color , and dead cells in blue color
 */
class CellPanel extends JPanel
{
	/** CellPanel() is a constructor which constructs the cells 	
	 */
	public CellPanel()
	{
		setBackground(Color.BLUE);
		//Functionality when a mouse click is done on a cell, if clicked cell changes to black color
		//	else it will stay in blue color
        addMouseListener(new MouseAdapter() 
		{
			
            public void mouseClicked(MouseEvent me) {
                if(getBackground() == Color.BLACK) {
                  setBackground(Color.BLUE);  
                }
                else{
                    setBackground(Color.BLACK);
                }
            }
        });
	}

}


class GraphicConways
{
	public static void main(String args[])
	{	
		//checks if the user gave both the dimensions of frame
		if(args.length == 2)
		{	
			//holds the dimension for rows, should be final because we are accesing it in the inner class
			final int rows = Integer.parseInt(args[0]);
			//holds the dimension for columns, should be final because we are accesing it in the inner class
			final int columns = Integer.parseInt(args [1]);
			
			//invokeLater is a static method that execute the run() method
			EventQueue.invokeLater(new Runnable() {
                public void run() {
                    MainWindow game = new MainWindow(rows,columns);
					game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//game.pack();
					game.setVisible(true);
					
                } 
            });
			
		}
		else
		{
			System.out.println("Please enter both the grid dimensions");
		}
		
	}
}
