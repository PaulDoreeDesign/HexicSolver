package Hexic_Solver;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
//import java.util.Timer;



import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;

public class MyFrame extends JFrame 
{
    private MyPanel myPanel;
    private final int FRAMEW = 800;
    private final int FRAMEH = 620;
    int score;
    int level;
    int comborem;
    Container contentPane;
    JButton btnGenerate;
    JButton btnClear;
    JLabel lblScore;
    JLabel lblLevel;
    JLabel lblComboRem;

    
	public MyFrame() 
	{
		setSize(FRAMEW,FRAMEH);
		
		addWindowListener(new WindowCloser());
		
		contentPane = getContentPane();
        myPanel = new MyPanel();
        contentPane.add(myPanel);
        myPanel.setLayout(null);
        
        JLabel lblSCORE = new JLabel("SCORE");
        lblSCORE.setForeground(Color.WHITE);
        lblSCORE.setFont(new Font("Arial", Font.BOLD, 24));
        lblSCORE.setHorizontalAlignment(SwingConstants.CENTER);
        lblSCORE.setBounds(244, 22, 104, 33);
        myPanel.add(lblSCORE);
        
        JLabel lblLEVEL = new JLabel("LEVEL");
        lblLEVEL.setForeground(Color.WHITE);
        lblLEVEL.setHorizontalAlignment(SwingConstants.CENTER);
        lblLEVEL.setFont(new Font("Arial", Font.BOLD, 18));
        lblLEVEL.setBounds(6, 103, 104, 33);
        myPanel.add(lblLEVEL);
        
        JLabel lblCOMBOS = new JLabel("COMBOS");
        lblCOMBOS.setForeground(Color.WHITE);
        lblCOMBOS.setHorizontalAlignment(SwingConstants.CENTER);
        lblCOMBOS.setFont(new Font("Arial", Font.BOLD, 18));
        lblCOMBOS.setBounds(6, 176, 104, 33);
        myPanel.add(lblCOMBOS);
        
        JLabel lblREMAINING = new JLabel("REMAINING");
        lblREMAINING.setForeground(Color.WHITE);
        lblREMAINING.setHorizontalAlignment(SwingConstants.CENTER);
        lblREMAINING.setFont(new Font("Arial", Font.BOLD, 18));
        lblREMAINING.setBounds(6, 196, 104, 33);
        myPanel.add(lblREMAINING);
        
        score = 0;
        lblScore = new JLabel(score+"");
        lblScore.setForeground(Color.WHITE);
        lblScore.setHorizontalAlignment(SwingConstants.TRAILING);
        lblScore.setFont(new Font("Arial", Font.BOLD, 24));
        lblScore.setBounds(407, 22, 104, 33);
        myPanel.add(lblScore);
        
        
        level = 1;
        lblLevel = new JLabel(level+"");
        lblLevel.setForeground(Color.WHITE);
        lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
        lblLevel.setFont(new Font("Arial", Font.BOLD, 18));
        lblLevel.setBounds(6, 131, 104, 33);
        myPanel.add(lblLevel);
        
        comborem = 50;
        lblComboRem = new JLabel(comborem+"");
        lblComboRem.setForeground(Color.WHITE);
        lblComboRem.setHorizontalAlignment(SwingConstants.CENTER);
        lblComboRem.setFont(new Font("Arial", Font.BOLD, 18));
        lblComboRem.setBounds(6, 225, 104, 33);
        myPanel.add(lblComboRem);
        
        btnGenerate = new JButton("Generate");
        btnGenerate.setBounds(648, 107, 117, 29);
        btnGenerate.addActionListener(new MyBtnListener());
        myPanel.add(btnGenerate);
        
        JLabel lblARotate = new JLabel("A - Rotate Left");
        lblARotate.setForeground(Color.WHITE);
        lblARotate.setBounds(680, 370, 104, 16);
        myPanel.add(lblARotate);
        
        JLabel lblSRotate = new JLabel("S - Rotate Right");
        lblSRotate.setForeground(Color.WHITE);
        lblSRotate.setBounds(680, 449, 104, 16);
        myPanel.add(lblSRotate);
        
        JLabel lblDisclaimer = new JLabel("This program is for educational purposes. Hexic is a registered trademark of the Microsoft Corporation.");
        lblDisclaimer.setForeground(Color.LIGHT_GRAY);
        lblDisclaimer.setBounds(60, 576, 678, 16);
        myPanel.add(lblDisclaimer);
        
        btnClear = new JButton("Clear\t Clusters");
        btnClear.setBounds(648, 135, 117, 29);
        btnClear.addActionListener(new MyBtnListener());
        myPanel.add(btnClear);
	}
	
	protected void updateLabels()
	{
		Board tmpBoard = myPanel.getBoard();
		score = tmpBoard.getScore();
		lblScore.setText(score+"");
		level = tmpBoard.getLevel();
		lblLevel.setText(level+"");
		comborem = tmpBoard.getComborem();
		lblComboRem.setText(comborem+"");
	}
	
	private class MyBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object source = event.getSource();
			if (source == btnGenerate)
			{
				myPanel.genNewBoard();
				updateLabels();
			}
			else if (source == btnClear)
			{
				myPanel.ClearThingsExt();
				updateLabels();
			}
			myPanel.requestFocus(); // passes focus back to panel so keys can be inputted
		}
	}

    private class WindowCloser extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
        	System.exit(0);
        }
    }
}
class MyPanel extends JPanel
{
	private SimpleGraph graph;
	private Board board;
	private Ellipse2D nodeToRotateAround;
	private Star starToRotate;
	private Timer timer;
	//private boolean timerOn;
	
	public MyPanel()
	{
		super();
		board = new Board();
		graph = new SimpleGraph(board);
		
		addMouseMotionListener(new MouseMotionHandler());
		addKeyListener(new KeyHandler());
		setFocusable(true); // needed for key listener
		
		timer = new Timer(100, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	updateLabels();
		        repaint();
		    }
		});
	}
	public void genNewBoard()
    {
		board = new Board();
		graph.setBoard(board);
        repaint();
    }
	public void ClearThingsExt()
	{
		board.clearThings();
		repaint();
	}
	
	private void updateLabels()
	{
		MyFrame topFrame = (MyFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.updateLabels();
	}
	
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        graph.draw(g2);
        if(nodeToRotateAround!=null)
        {
        	g2.setColor(Color.black);
        	g2.draw(nodeToRotateAround);
        	g2.setColor(Color.white);
        	g2.fill(nodeToRotateAround);
        }
        	
    }
	public Board getBoard()
	{
		return board;
	}
	
	private void rotationCluster(String sIn, int xIn, int yIn)
	{
		timer.start();
		final String s = sIn;
		final int x = xIn;
		final int y = yIn;
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() 
			{
				boolean stopRotation = false;
				for(int i=0;i<3 && !stopRotation;i++)
				{
					board.rotateCluster(s,x,y);
					try
					{
						Thread.sleep(400);
					}
					catch(Exception e)
					{
						System.out.println("thread didn't sleep after rotation");
					}
					if(board.clearingNeeded())
					{
						stopRotation = true;
						while(board.clearingNeeded())
						{
							board.clearThings();
							try
							{
								Thread.sleep(800);
							}
							catch(Exception e)
							{
								System.out.println("thread didn't sleep after rotation");
							}
						}
					}
				}
				timer.stop();
			}
		});
		t.start();
	}
	
	private void rotationStar(String sIn, int xIn, int yIn)
	{
		final String s = sIn;
		final int x = xIn;
		final int y = yIn;
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() 
			{
				board.rotateStar(s,x,y);
				try
				{
					Thread.sleep(400);
				}
				catch(Exception e)
				{
					System.out.println("thread didn't sleep after rotation");
				}
				while(board.clearingNeeded())
				{
					board.clearThings();
					try
					{
						Thread.sleep(800);
					}
					catch(Exception e)
					{
						System.out.println("thread didn't sleep after rotation");
					}
				}
				timer.stop();
			}
		});
		t.start();
	}
	
	private class MouseMotionHandler extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent event)
		{
			// mouse in circle between hexagons
			Ellipse2D tmpCircle = graph.isTheMouseInACircle(event.getPoint());
			if(tmpCircle!=null)
				nodeToRotateAround = tmpCircle;
			else
				nodeToRotateAround = null;
			// mouse in star
			Star tmpStar = graph.isTheMouseInAStar(event.getPoint());
			if(tmpStar!=null)
				starToRotate = tmpStar;
			else
				starToRotate = null;
			
			repaint();
		}
	}
	private class KeyHandler extends KeyAdapter
	{
		public void keyTyped(KeyEvent event)
		{
			char keyChar = event.getKeyChar();
			if(keyChar == 'a')
			{
				// rotate left
				if(nodeToRotateAround!=null)
				{
					Position posi = graph.getPosCircle(nodeToRotateAround);
					rotationCluster("Left", posi.getX(), posi.getY());
					repaint();
				}
				else if(starToRotate!=null)
				{
					Position posi = starToRotate.getPos();
					rotationStar("Left", posi.getX(), posi.getY());
					repaint();
				}
			}
			else if(keyChar == 's')
			{
				// rotate right
				if(nodeToRotateAround!=null)
				{
					Position posi = graph.getPosCircle(nodeToRotateAround);
					rotationCluster("Right", posi.getX(), posi.getY());
					repaint();
				}
				else if(starToRotate!=null)
				{
					Position posi = starToRotate.getPos();
					rotationStar("Right", posi.getX(), posi.getY());
					repaint();
				}
			}
			else if(keyChar == 'w')
			{
				if(nodeToRotateAround!=null)
				{
					Position posi = graph.getPosCircle(nodeToRotateAround);
					System.out.println("Node: x="+posi.getX()+", y="+posi.getY());
				}
				else if(starToRotate!=null)
				{
					Position posi = starToRotate.getPos();
					System.out.println("Star: x="+posi.getX()+", y="+posi.getY());
				}
			}
			else if(keyChar == 'd')
			{
				if(board.clearingNeeded())
					System.out.println("Clearing needed!");
			}
			else
			{
			}
		}
	}
}

