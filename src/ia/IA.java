package ia;

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
		LinkedList<Cuadro> cquemepuedomover = new LinkedList<Cuadro>();
		LinkedList<Cuadro> cquepuedoAtacarsimemuevoono = new LinkedList<Cuadro>();
		for (Cuadro c : t.getCuadros()){
			if (n.puedoMovermeA(c)){
				cquemepuedomover.add(c);
			}
			if (n.distancia(enemigo.getCuadro(),c,n.distAtt) && n.puedoMovermeA(c)){
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
	
	//TODO: POCKIE: metodo para encontrar los cuadros a los que efecivamente puede llegar,
	//teniendo en cuenta obstaculos o ninjas que estorben su camino
	public LinkedList<Cuadro> puedeMoverseA(Ninja n, Cuadro c, Tablero t){
		int cantpasos= n.getDistMov();
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>();
		pasos.add(n.getCuadro());
		
		Cuadro temp = null;
		
		int nposx = n.getCuadro().getPosRelX();
		int nposy = n.getCuadro().getPosRelY();
		int cposx = c.getPosRelX();
		int cposy = c.getPosRelY();
		
		while (cantpasos > 0){
			if (!puedeMoverse(n,t,pasos)){
				return null;
			}
			else{
				if (puedeAcercarse(n,c,t,pasos)){
					temp = acercarse(n,c,t,pasos);
					pasos.add(temp);
					cantpasos--;
				}
				else {
					if (puedeEvadir(n,c,t,pasos)){
						temp = evadir(n,c,t,pasos);
						pasos.add(temp);
						cantpasos--;
					}
				}
			}
		}
		return pasos;
	}
	
	protected boolean puedeMoverse(Ninja n, Tablero t, LinkedList<Cuadro> pasos){
		 LinkedList<Cuadro> adyacentes = adyacentes(n,t);
		 
		 boolean rta = false;
		 
		 for (int i = 0; i<4;i++){
			 if (adyacentes.get(i).ninjaIsNull() && !pasos.contains(adyacentes.get(i))){
				 rta = true;
				 i = 4;
			 }
		 }
		 return rta;
	 }
	 
	 protected boolean puedeAcercarse(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 return (puedeAcercarsePorX(n,c,t, pasos)||puedeAcercarsePorY(n,c,t, pasos));
	 }
	 
	 protected boolean puedeAcercarsePorX(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 
		 if (estaEnXMenor(n,c)){
			 return adyacenteXVacio(n,t,1,pasos);
		 }
		 else{ return adyacenteXVacio(n,t,-1, pasos);
		 }
	 }
	 
	 protected boolean estaEnXMenor(Ninja n, Cuadro c){
		 int  nx = n.getCuadro().getPosRelX();
		 int cx = c.getPosRelX();
		 return (nx<cx);
	 }
	 
	 protected boolean puedeAcercarsePorY(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 
		 if (estaEnYMenor(n,c)){
			 return adyacenteYVacio(n,t,1, pasos);
		 }
		 else{ return adyacenteYVacio(n,t,-1, pasos);
		 }
	 }
	 
	 protected boolean estaEnYMenor(Ninja n, Cuadro c){
		 int  ny = n.getCuadro().getPosRelY();
		 int cy = c.getPosRelY();
		 return (ny<cy);
	 }
	 
	 protected Cuadro acercarse(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 int indexCuadroNinja = t.getCuadros().indexOf(n.getCuadro());
		 Cuadro rta = null;
		 
		 if (puedeAcercarsePorX(n, c, t,pasos)){
			 if(estaEnXMenor(n,c)){
				 if (!pasos.contains(t.getCuadros().get(indexCuadroNinja+1))){
				 rta = t.getCuadros().get(indexCuadroNinja+1);}
				 
			 }else {
				 if (!pasos.contains(t.getCuadros().get(indexCuadroNinja-1))){
				 rta = t.getCuadros().get(indexCuadroNinja-1);}}
			 }
		else if (puedeAcercarsePorY(n,c,t,pasos)){
			  if(estaEnYMenor(n,c)){
				  if(!pasos.contains(t.getCuadros().get(indexCuadroNinja+1))){
				  rta = t.getCuadros().get(indexCuadroNinja+9);}
				  
			 }else {
				 if(!pasos.contains(t.getCuadros().get(indexCuadroNinja+1))){
				 rta = t.getCuadros().get(indexCuadroNinja-9);}}
			 }
		 return rta;
		 }
	 
	 protected Cuadro evadir(Ninja n,Cuadro c,Tablero t, LinkedList<Cuadro> pasos){
		 int indexCuadroNinja = t.getCuadros().indexOf(n.getCuadro());
		 Cuadro rta = null;
		 
		 if (puedeEvadirPorX(n, c, t, pasos)){
			 if(estaEnXMenor(n,c)){
				 if(!pasos.contains(t.getCuadros().get(indexCuadroNinja-1))){
				 rta = t.getCuadros().get(indexCuadroNinja-1);}
				 
			 }else {
				 if(!pasos.contains(t.getCuadros().get(indexCuadroNinja+1))){
				 rta = t.getCuadros().get(indexCuadroNinja+1);}}
			 }
		else if (puedeEvadirPorY(n,c,t,pasos)){
			  if(estaEnYMenor(n,c)){
				  if(!pasos.contains(t.getCuadros().get(indexCuadroNinja-9))){
				  rta = t.getCuadros().get(indexCuadroNinja-9);}
				  
			 }else {
				 if(!pasos.contains(t.getCuadros().get(indexCuadroNinja+9))){
				 rta = t.getCuadros().get(indexCuadroNinja+9);}}
			 }
		 return rta;
		 }
	 
	 public boolean adyacenteXVacio(Ninja n, Tablero t, int i, LinkedList<Cuadro> pasos){
			
		 Cuadro cuadroNin = n.getCuadro();
		 int indexCuadro = t.getCuadros().indexOf(cuadroNin);
		 Cuadro adyacente = t.getCuadros().get(indexCuadro+i);
		 return adyacente.ninjaIsNull() && !pasos.contains(adyacente);
	 }
	 
	 public boolean adyacenteYVacio(Ninja n, Tablero t, int i, LinkedList<Cuadro> pasos){
			
		 Cuadro cuadroNin = n.getCuadro();
		 int indexCuadro = t.getCuadros().indexOf(cuadroNin);
		 Cuadro adyacente = t.getCuadros().get(indexCuadro+(9*i));
		 return adyacente.ninjaIsNull()&& !pasos.contains(adyacente);
	 }
	 
	 public LinkedList<Cuadro> adyacentes(Ninja n, Tablero t){
		 
		 LinkedList<Cuadro> adyacentes = new LinkedList<Cuadro>();
		 int indexCuadroNinja = t.getCuadros().indexOf(n.getCuadro());
		 adyacentes.add(t.getCuadros().get(indexCuadroNinja+1));
		 adyacentes.add(t.getCuadros().get(indexCuadroNinja-1));
		 adyacentes.add(t.getCuadros().get(indexCuadroNinja+9));
		 adyacentes.add(t.getCuadros().get(indexCuadroNinja-9));
		 
		 return adyacentes;
	 }
	 
	 public boolean puedeEvadir(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 return (puedeEvadirPorX(n,c,t, pasos)||puedeEvadirPorY(n,c,t, pasos));
	 }
	 
	 public boolean puedeEvadirPorY(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 if (estaEnYMenor(n,c)){
			 return adyacenteYVacio(n,t,-1, pasos);
		 }
		 else{ return adyacenteYVacio(n,t,1, pasos);
		 }
	 }
	 
	 public boolean puedeEvadirPorX(Ninja n, Cuadro c, Tablero t, LinkedList<Cuadro> pasos){
		 if (estaEnXMenor(n,c)){
			 return adyacenteXVacio(n,t,-1, pasos);
		 }
		 else{ return adyacenteXVacio(n,t,1,pasos);
		 }
	 }
}
