package ia;

import java.util.LinkedList;
import java.util.Random;

import ninjas.EquipoNinja;
import ninjas.Ninja;
import tablero.Cuadro;
import tablero.Tablero;

public class IACompleja extends IA {
	
	public IACompleja(){
		super();
	}
	
	public synchronized void accionAutomatica(Ninja n, Tablero t){
		
		//TODO: POCKIE: IA compleja.. pensarla bien
		
		boolean b = true;
		LinkedList<Ninja> enemigos = encontrarLosQueEstanAlAlcance(n, t);
//		Ninja oponenteAAtacar = elegirElQueConvieneAtacar(n,enemigos);
		
		acercarse(n,enemigos,t);
		
		/* encontrar lista de los que puede atacar moviendose o no
		  elegir uno random
		  acercarse (moverseparatacar)?
		  atacarlo
		 */
	}
//	acercarseParaAtacar(n,t);-> 
	/* 	-
//	acercarse si se puede en x o y
//		si no puedo evadir en x
//			si no puedo evadir en y
* 					si no puedo evadir en ninguno, no puedo llegar ->	acercarse si se puede al siguiente oponente mas cercano/
* 																		/acercarse al siguiente cuadro mas cercano donde puede atacar al op mas cercano
//	(guardar siempre el camino que esta recorriendo -> lista de cuadros anteriores)
-->acercarse si se puede en x o y...
	
	antes de acercarse chequea si esta en el cuadro
//	*/
//	atacar()->/*-
//				-	(armar algoritmo)
//				-*/
	
//}
	private LinkedList<Ninja> encontrarLosQueEstanAlAlcance(Ninja n, Tablero t){
		LinkedList<Ninja> enemigos = t.getEnemigos(n).ninjas;
		LinkedList<Ninja> losQuePuedoAtacar = new LinkedList<Ninja>();
		for (Ninja ninEn : enemigos ){
			if (n.distancia(n.getCuadro(),ninEn.getCuadro(), n.getDistAtt()+n.getDistMov())){
				losQuePuedoAtacar.add(ninEn);
			}
		}
		return losQuePuedoAtacar;
	}
	
	protected void acercarse(Ninja n, LinkedList<Ninja> enemigos, Tablero t) {
		Random r = new Random();
		LinkedList<Cuadro> cquemepuedomover = new LinkedList<Cuadro>();
		LinkedList<Cuadro> cquepuedoAtacarsimemuevoono = new LinkedList<Cuadro>();
		boolean b = true;
		Ninja enemigo;
		
		while (b){
			enemigo = enemigos.getFirst();
			enemigos.removeFirst();
			
			if (n.puedeAtacar(enemigo)){
				n.moverseParaAtacar(enemigo, t);
				b=true;
			} 
			else {
//				acercarsePorXoY(n,enemigo,t);				
			}
		}
		
		
		
//		for (Cuadro c : t.getCuadros()){
//			if (n.puedoMovermeA(c)){
//				cquemepuedomover.add(c);
//			}
//			if (n.distancia(enemigo.getCuadro(),c,n.distAtt) && n.puedoMovermeA(c)){
//				cquepuedoAtacarsimemuevoono.add(c);
//			}
//		}
//		
//		if (cquepuedoAtacarsimemuevoono.size() != 0){
//			n.movete(cquepuedoAtacarsimemuevoono.get(r.nextInt(cquepuedoAtacarsimemuevoono.size())));
//		}
//		else if (cquemepuedomover.size() != 0){
//			n.movete(cquemepuedomover.get(r.nextInt(cquemepuedomover.size())));
//		}
//		else n.rest();
	}

}
