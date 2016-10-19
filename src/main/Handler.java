package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import enums.IDEquipo;
import ninjas.EquipoNinja;
import ninjas.Ninja;
import tablero.Cuadro;
import tablero.Tablero;

public class Handler {
	
	private int pantW;
	private int pantH;
	
	private Tablero tab;
	
	private Random r = new Random();
	
	
	private Game game;
	
	LinkedList<GameObject> obj;
	
	public Handler(int w, int h, Game g){
		this.game = g;
		pantW = w;
		pantH = h;
		obj = new LinkedList<GameObject>();
		agregarTablero();
	}
	
	public void tick(){
		for(GameObject go : obj){
			go.tick();
		}
	}
	
	public void render(Graphics g){
		for(GameObject go : obj){
			go.render(g);
		}
	}
	
	public void addObject(GameObject obj){
		this.obj.add(obj);
	}
	
	public void removeObject(GameObject object){
		this.obj.remove(object);
	}
	
	public void agregarNinPrueba(){
		
		int pos1 = r.nextInt(44);
		int pos2 = r.nextInt(44);

		Cuadro c = tab.getCuadros().get(pos1);
		Ninja n =new Ninja("sasuke",c,15);
		n.colorNormal = Color.GREEN;
		c.setNinja(n);
		
		LinkedList<Ninja> l1 = new LinkedList<Ninja>(); l1.add(n);
		EquipoNinja e1 = new EquipoNinja(IDEquipo.A,l1);
		
		c = tab.getCuadros().get(pos2); 
		n = new Ninja("naruto",c,15);
		n.colorNormal = Color.RED; 
		c.setNinja(n);
		
		l1 = new LinkedList<Ninja>(); l1.add(n);
		
		EquipoNinja e2 = new EquipoNinja(IDEquipo.B,l1);
		tab.setEquipoA(e1);
		tab.setEquipoB(e2);
		
	}
	
	private void agregarTablero(){
		tab = new Tablero(5,9,pantH,pantW);
		this.obj.add(tab);
		new MouseEventsFight(this.game, tab);
	}
	
	public Tablero getTablero(){
		return tab;
	}

}
