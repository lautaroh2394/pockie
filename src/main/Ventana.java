package main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Ventana extends Canvas{	
	
	private static final long serialVersionUID = -240840600533728354L;
	
	public Ventana(int w, int h, String title,Game game){
		
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(w,h));
		frame.setMaximumSize(new Dimension(w,h));
		frame.setMinimumSize(new Dimension(w,h));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);//la ventana se abre en el centro de la pantalla en vez de la esquina
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
	

}
