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
}
