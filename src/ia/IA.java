package ia;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import ninjas.Ninja;
import tablero.Cuadro;
import tablero.Tablero;

public class IA {

	public IA(){
		
	}
	
	public void accionAutomatica(Ninja n, Tablero t){
		
	}
	
	protected void moverseParaAtacar(Ninja n, Ninja enemigo, Tablero t) {
		Random r = new Random();
		LinkedList<Cuadro> cquemepuedomover = posicionesALasQuePuedeIr(n, t);
		LinkedList<Cuadro> cquepuedoAtacarsimemuevoono = new LinkedList<Cuadro>();
		for (Cuadro c : t.getCuadros()){
			if (n.distancia(enemigo.getCuadro(),c,n.distAtt) && cquemepuedomover.contains(c)){
				cquepuedoAtacarsimemuevoono.add(c);
			}
		}
		
		if (cquepuedoAtacarsimemuevoono.size() != 0){
			n.movete(cquepuedoAtacarsimemuevoono.get(r.nextInt(cquepuedoAtacarsimemuevoono.size())));
		}
		else if (cquemepuedomover.size() != 0){
			n.movete(cquemepuedomover.get(r.nextInt(cquemepuedomover.size())));
		}
		else n.rest();
	}
	
	public LinkedList<Cuadro> posicionesALasQuePuedeIr(Ninja n, Tablero t){
		int i = 0;
		boolean fin = false;
		HashSet<Cuadro> temp;
		LinkedList<Cuadro> posQuePuedeIr = new LinkedList<Cuadro>();
		posQuePuedeIr.add(n.getCuadro());
		int pos = posQuePuedeIr.size();
		
		while ( i < n.getDistMov() && !fin){
			temp = new HashSet<Cuadro>();
			
			for (int j = pos-1; j<posQuePuedeIr.size();j++){
				temp.addAll(adyacentes(posQuePuedeIr.get(j),t));
			}
			
			if (temp ==null) {}//fin = true;
			else {
				temp = sacarLosQueYaEstanEn(posQuePuedeIr,temp);
				posQuePuedeIr.addAll(temp);
				
			}
			i++;
		}
		return posQuePuedeIr;
	}
	
	public HashSet<Cuadro> adyacentes(Cuadro c, Tablero t){
		HashSet<Cuadro> ady = new HashSet<Cuadro>();
		int index = t.getCuadros().indexOf(c);
//		int col = index % 9;
//		int fila = Math.floorDiv((index -col),5);
		
		int col = c.getPosRelX();
		int fila = c.getPosRelY();
		
		if (0==col){ ady.add(adyXsiguiente(c,t)); }
		else if (8 == col) {ady.add(adyXanterior(c,t));}
		else {ady.add(adyXanterior(c,t));ady.add(adyXsiguiente(c,t));}
		
		if (0 == fila){ ady.add(adyYsiguiente(c,t));}
		else if (4 == fila){ady.add(adyYanterior(c,t));}
		else {ady.add(adyYsiguiente(c,t));ady.add(adyYanterior(c,t));}
		
		HashSet<Cuadro> ocupados = ocupados(ady,t);
		ady.removeAll(ocupados);
		return ady;
	}
	
	public HashSet<Cuadro> ocupados(HashSet<Cuadro> cs, Tablero t){
		HashSet<Cuadro> ocupados = new HashSet<Cuadro>();
		int index;
		for (Cuadro c: cs){
			index = t.getCuadros().indexOf(c);
			if (!t.getCuadros().get(index).ninjaIsNull()){
				ocupados.add(c);
			}
		}
		
		return ocupados;
	}
	
	public Cuadro adyXsiguiente(Cuadro c, Tablero t){
		int index = t.getCuadros().indexOf(c);
		return t.getCuadros().get(index+1);
	}
	
	public Cuadro adyYsiguiente(Cuadro c, Tablero t){
		int index = t.getCuadros().indexOf(c);
		return t.getCuadros().get(index+9);
	}
	
	public Cuadro adyXanterior(Cuadro c, Tablero t){
		int index = t.getCuadros().indexOf(c);
		return t.getCuadros().get(index-1);
	}
	
	public Cuadro adyYanterior(Cuadro c, Tablero t){
		int index = t.getCuadros().indexOf(c);
		return t.getCuadros().get(index-9);
	}
	
	public HashSet<Cuadro> sacarLosQueYaEstanEn(LinkedList<Cuadro> pos, HashSet<Cuadro> temp){
		HashSet<Cuadro> t = new HashSet<Cuadro>();
		for (Cuadro c: temp){
			if (!pos.contains(c)){t.add(c);};
		}
		return t;
	}

}
