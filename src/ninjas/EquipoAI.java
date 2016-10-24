package ninjas;

import java.util.Iterator;
import java.util.LinkedList;

import enums.IDEquipo;
import tablero.Tablero;

public class EquipoAI extends EquipoNinja {

	public EquipoAI(IDEquipo id, LinkedList<Ninja> n){
		super(id,n);

	}
	public EquipoAI(IDEquipo id){
		super(id, new LinkedList<Ninja>());
	}
	
	@Override
	public void tick(){
			for (Ninja n : ninjas){
				n.tick();
				n.IA();
				}
		}

}
