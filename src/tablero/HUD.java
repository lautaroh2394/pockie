package tablero;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import ninjas.EquipoNinja;

public class HUD {
	//Este hud esta armado para dos equipos, si en algun momento decido que estaria piola
	//hacer peleas de mas de 2 equipos va a haber que modificar esta clase o armar un hijo que maneje 3 o mas equipos
	//Sobreentiende que estan en orden en la lista equipos-> 1ero a la izq, 2do a la der 

	public LinkedList<EquipoNinja> equipos;
	public int ancho;
	public int altura;
	
	public HUD(int h, int a){
		ancho = a;
		altura = h;
	}
	
	public HUD(){
		
	}
	
	private float getPorcVida(EquipoNinja e){
		return  (100*(e.getVidaActual()/e.getVidaTotal()));
	}
	
//	public void tick(){
//		
//	}
//	
	public void render(Graphics g){
		renderBarrasVida(g);
	}

	private void renderBarrasVida(Graphics g){
		int espacio =(int) (ancho*0.03);
		int posI1 = espacio;
		int posF1 = (int) (ancho/2 - espacio);
		int posI2 = (int) (ancho/2 + espacio);
		int posF2 = (int) (ancho - espacio);
		//asumiendo que hay 2 equipos siempre
		if (equipos.size()==2){
			
				renderBarra(g,equipos.get(0), posI1, posF1-posI1);
				renderBarra(g,equipos.get(1), posI2, posF2-posI2);
				renderEstrellas(g,equipos.get(0), posI1, posF1);
			
			}
		}
	
	private void renderBarra(Graphics g,EquipoNinja e, int posi, int anchoBarra){
		
		int alt= (int) (altura * 0.01);
		int altodebarra = altura/50;
		int anchoBarraVidaActual = (int) ((getPorcVida(e)/100)*(anchoBarra));
		g.setColor(new Color(130,25,70));
		g.fillRect(posi, alt, anchoBarra, altodebarra);
		g.setColor(new Color(45,145,90));
		g.fillRect(posi, alt, anchoBarraVidaActual, altodebarra);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font(null, 10, (int) ((altura/50)*0.9)));
		g.drawString((int) (getPorcVida(e))+"%", (int) (posi+anchoBarraVidaActual*0.94), (int) (altodebarra+alt*0.95));
		
	}
	
	private void renderEstrellas(Graphics g, EquipoNinja e, int posi, int posf){
		int alt = (int) (altura*0.05);
		int cant = Math.floorDiv((int) (getPorcVida(e)), 20);
		int intervaloE = (int) ((posf-posi)/5) ;
		
		g.setColor(Color.yellow);
		for (int i = 0; i< cant; i++){
			g.fillRoundRect(posi+(i*intervaloE)+intervaloE/2, alt, 10, 10, 10,10);
		}
	}

	public void setEquipos(LinkedList<EquipoNinja> e){
		this.equipos = e;
	}
}
