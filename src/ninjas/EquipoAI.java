package ninjas;

import java.util.LinkedList;

import enums.IDEquipo;
import ia.iaMovGrilla;

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
	
	@Override
	public void addNinja(Ninja n){
		super.addNinja(n);
		n.setIA(new iaMovGrilla());
	}

}
