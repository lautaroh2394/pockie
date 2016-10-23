package tablero;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import enums.OpcionHabilitada;
import ninjas.Ninja;

public abstract class OpcionMenuNinja {
	
	protected int posX;
	protected int posY;
	protected int ancho;
	protected int alto;
	protected int posStrX;
	protected int posStrY;
	
	protected MenuNinja menu;
	
	protected OpcionHabilitada enabled;
	
	protected String texto = "Atacar";
	
	public OpcionMenuNinja(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX,
			int posSY, MenuNinja m) {
		this.menu = m;
		this.posX = posOpcionCX;
		this.posY = posOpcionCY;
		this.ancho = anchoOpcionC;
		this.alto = altoOpcionC;
		this.posStrX = posSX;
		this.posStrY = posSY;
	}

	public void render(Graphics g, Font f, Color fondo, Color str) {
		g.setFont(f);
		if (enabled()){	g.setColor(fondo);} else g.setColor(Color.gray); 
		g.fillRect(posX, posY, ancho, alto);
		g.setColor(str);
		g.drawString(texto, posStrX, posStrY);		
	}

	public boolean meClickearon(int mx, int my) {
		if (mx > posX && mx < posX + ancho && my > posY && my < posY + alto && enabled()){
			return true;
			}
		else return false;
	}

	public abstract boolean queHacer(int mx, int my, Ninja c);
	
	private boolean enabled(){
		return (this.enabled == OpcionHabilitada.enabled);
	}

}
