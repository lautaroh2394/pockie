package tablero;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import enums.StateMenu;
import ninjas.Ninja;

public class MenuNinja {

	private int posx;
	private int posy;

	private int ancho;
	private int alto;
	
	private int nroRelativo;
	
	private boolean ocultarM = false;

	//public static StateMenu estadoMenu = StateMenu.NoInstanciado;

	private Ninja n;

	private LinkedList<OpcionMenuNinja> opciones;

	public MenuNinja(Ninja n) {
		this.n = n;
		//MenuNinja.estadoMenu = StateMenu.Instanciado;
		Tablero.stateMenu = StateMenu.Instanciado;
		this.posx = (int) (n.getCuadro().getX() + n.getCuadro().getWidth() * 0.1);
		this.posy = (int) (n.getCuadro().getY() + n.getCuadro().getHeight() * 0.1);
		this.ancho = (int) (n.getCuadro().getWidth() * 0.85);
		this.alto = (int) (n.getCuadro().getHeight() * 0.85);
		//this.alto = (int) (n.getCuadro().getHeight() * 1.15);
		nroRelativo = alto/ancho;
		initOpciones();

	}

	public void render(Graphics g) {
		if (!ocultarM){
		renderCuerpo(g);
		renderOpciones(g);
		}
	}

	private void renderCuerpo(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(posx, posy, ancho, alto);
	}

	private void renderOpciones(Graphics g) {
		// son 4 opciones

		Font f = new Font(null, 0, ((alto - 6*nroRelativo) / 4*nroRelativo));

		for (OpcionMenuNinja o : opciones) {
			o.render(g, f, new Color(20, 25, 20), Color.white);
		}
	}

	public void decidiQueHacer(int mx, int my, Cuadro c) {

		for (int i = 0; i < opciones.size(); i++) {

			if (opciones.get(i).queHacer(mx, my, c)) {
				i = opciones.size();
			}
		}
	}

	private void initOpciones() {
		
		int cantOpciones = 4;

		opciones = new LinkedList<OpcionMenuNinja>();

		
		int posOpcionCX = posx + 2*nroRelativo;
		int posOpcionCY = posy + 2*nroRelativo;
		int anchoOpcionC = ancho - 4*nroRelativo;
		int altoOpcionC = (alto - 6*nroRelativo) / cantOpciones*nroRelativo;
		int posSX = posOpcionCX + 4*nroRelativo;
		int posSY = posOpcionCY + 13*nroRelativo;

		for (int i = 0; i < cantOpciones; i++) {
			if (i == 0)
				opciones.add(new OpcionMover(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, this));
			else if (i == 1)
				opciones.add(new OpcionAtacar(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, this));
			else if (i == 2)
				opciones.add(new OpcionCancel(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, this));
			else if (i == 3)
				opciones.add(new OpcionRest(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, this));
			posOpcionCY += 1*nroRelativo + altoOpcionC;
			posSY = posOpcionCY + 13*nroRelativo;
		}
	}

	public Ninja getN() {
		return n;
	}

	public void setN(Ninja n) {
		this.n = n;
	}
	
	public void ocultarMenu(){
		this.ocultarM = true;
	}

}
