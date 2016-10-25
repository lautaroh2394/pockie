package repositorioNinjas;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import enums.IDEquipo;
import ia.IABasica;
import ninjas.EquipoAI;
import ninjas.EquipoNinja;
import ninjas.Ninja;
import tablero.Cuadro;
import tablero.Tablero;

public class GeneradorNinjas {
	
	private int tam = 15;
	private Tablero tab;
	private int[] posEqA = {0,1,9,10,18,19,27,28,36,37};
	private int[] posEqB = {7,8,16,17,25,26,34,35,33,44};
	
	private Nombre nombre;
	
	public GeneradorNinjas(Tablero t){
		tab=t;
		nombre = new Nombre();
	}
	
	public EquipoNinja generarEquipo(int cant, IDEquipo id){
		
		Random r = new Random();
		EquipoNinja e = new EquipoNinja(id);
		
		for (int i = 0; i<cant; i++){
			e.addNinja(new Ninja(generarNombre(),generarCuadro(e,id),tam,new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255))));
		}
		
		return e;
	}
	
	public EquipoNinja generarEquipoColoresNombreHyI(int cant, IDEquipo id, Color nombreH, Color nombreI){
		
		Random r = new Random();
		EquipoNinja e = new EquipoNinja(id);
		
		Ninja temp = null;
		
		for (int i = 0; i<cant; i++){
			temp = new Ninja(generarNombre(),generarCuadro(e,id),tam,new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			temp.nombreInhabilitado = nombreI;
			temp.nombreNormal = nombreH;
			e.addNinja(temp);
		}
		
		return e;
	}

	public EquipoAI generarEquipoColoresNombreHyIAI(int cant, IDEquipo id, Color nombreH, Color nombreI){
		
		Random r = new Random();
		EquipoAI e = new EquipoAI(id);
		
		Ninja temp = null;
		
		for (int i = 0; i<cant; i++){
			temp = new Ninja(generarNombre(),generarCuadro(e,id),tam,new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
			temp.nombreInhabilitado = nombreI;
			temp.nombreNormal = nombreH;
			e.addNinja(temp);
		}
		e.setIA(new IABasica());
		return e;
	}
	
	public LinkedList<EquipoNinja> generarDosEquipos(int cant){
		LinkedList<EquipoNinja> eq = new LinkedList<EquipoNinja>();
		
		EquipoNinja a = generarEquipo(cant, IDEquipo.A);
		EquipoNinja b = generarEquipoColoresNombreHyI(cant, IDEquipo.B, Color.red, Color.blue);
		
		eq.add(a);eq.add(b);
		return eq;
	}
		
	public String generarNombre(){
		return nombre.devolverNombre();
		
	}
	
	private Cuadro generarCuadro(EquipoNinja e, IDEquipo id){
		boolean bandera = true;
		int[] pos;
		int c = 0;
		
		if (id == IDEquipo.A) {pos = posEqA;} 
		else {pos = posEqB;}
		Random r = new Random();
		
		while (bandera) {
			c = r.nextInt(pos.length);
			if (noEstaOcupado(pos[c])) {bandera = false;};
		}
		return tab.getCuadros().get(pos[c]);
		

	}
	
	private boolean noEstaOcupado(int c){
		return tab.getCuadros().get(c).ninjaIsNull();
	}
}
