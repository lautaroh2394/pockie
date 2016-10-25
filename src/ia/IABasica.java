package ia;

import java.util.LinkedList;

import ninjas.Ninja;
import tablero.Tablero;

public class IABasica extends IA{
	
	public long timer = 0;
	public long tiempodeespera = 50;
	
	public IABasica() {
		super();
	}
	
	//TODO: POCKIE Algoritmo de pathfinding - 
	//TODO: POCKIE metodo para que ninja muevase de a un cuadro por vez
	
	@Override
	public synchronized void accionAutomatica(Ninja n, Tablero t){
		//hay que refinar, pero la base base esta
		//si encuentra enemigo: busca una pos random desde donde atacarlo
		//si encuentra enemigo y no puede atacarlo por mas que se mueva: se mueve a una pos random de las que puede alcanzar
		//si no puede al enemigo, rest.
		//si no puede atacar ni moverse (banderas en true), el metodo que lo llama se encarga de decirle que 'restee'.

		timer++;
		Ninja enemigo = null;
		enemigo = t.buscarEnemigoCercano(n);
		
		if (!n.banderaMov && timer >= tiempodeespera){
			moverseParaAtacar(n,enemigo, t);
			timer = 0;
			}			
		if (!n.banderaAtt && timer>=tiempodeespera){
			if (n.puedeAtacar(enemigo)){
				n.atacaA(enemigo.getCuadro());
				timer = 0;			
				}
			else n.rest();
			}
		}
	
	
}
