package main;

import java.awt.Graphics;
import java.util.LinkedList;
import repositorioNinjas.GeneradorNinjas;
import tablero.HUD;
import tablero.Tablero;

public class Handler {
	
	private int pantW;
	private int pantH;
	
	private Tablero tab;
	
	
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
	
	public void agregarEquipos(){
		GeneradorNinjas gen = new GeneradorNinjas(tab);
		
		tab.setEquipos(gen.pruebagenerarDosEquipos());
	}
	
	public void agregarDosEquiposUnIA(){
		GeneradorNinjas gen = new GeneradorNinjas(tab);
		tab.setEquipos(gen.pruebagenerardosequiposunninjaAI());
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
