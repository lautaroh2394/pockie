package ninjas;

import java.awt.Color;
import java.awt.Graphics;

import enums.IDEquipo;
import tablero.Cuadro;

public class Obstaculo extends Ninja{
	
	IDEquipo idequipo = IDEquipo.Obs;
	public Obstaculo(String nombre, Cuadro cuadro, int t, Color n) {
		super(nombre, cuadro, t, n);		
	}

	@Override
	public void tick(){
		
	}
	
	@Override
	public void render(Graphics g){
		renderCuerpo(g);	
	}
	
	@Override
	public boolean seClickeo(int x, int w){
		return false;
	}

}
