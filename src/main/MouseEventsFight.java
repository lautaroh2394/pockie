package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import enums.IDEquipo;
import enums.StateMenu;
import tablero.MenuNinja;
import tablero.Tablero;

public class MouseEventsFight extends MouseAdapter{
	
	private Tablero tab;
	private Game game;
	
	public MouseEventsFight(Game game, Tablero tablero){
		this.game = game;
		this.tab = tablero;
		this.game.addMouseListener(this);
		
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();		
		
		eventosMenuNinja(mx, my);
	}
	
	private void eventosMenuNinja(int mx,int my){

		
		if (Tablero.stateMenu == StateMenu.NoInstanciado) tab.eventoClick(mx, my);
		
		else if( Tablero.stateMenu  == StateMenu.SeleccionCuadroMoverNinja) {
			tab.seleccionCuadroMov(mx,my);
			}
		else if( Tablero.stateMenu  == StateMenu.SeleccionCuadroAtacarNinja) {
			tab.seleccionCuadroAtt(mx,my);
			}
		else tab.eventoClick(mx, my);
	}

}
