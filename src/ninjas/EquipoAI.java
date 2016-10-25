package ninjas;

import java.awt.Graphics;
import java.util.LinkedList;

import enums.IDEquipo;
import ia.iaMovAMasCercano;

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
				IA();
				}
		}
	
	@Override
	public void addNinja(Ninja n){
		super.addNinja(n);
		n.setIA(new iaMovAMasCercano());
	}
	
	private synchronized void IA(){
		
		if (ninjaActivo== null) ninjaActivo = ninjas.get(0);
		ninjaActivo.IA();
		
		if (!ninjaActivo.puedeHacerAlgo()) {
			setSiguienteActivo();
		}
	}
	
	private void setSiguienteActivo(){
		int indexsiguiente = (ninjas.indexOf(ninjaActivo)+1)% ninjas.size();
		ninjaActivo = ninjas.get(indexsiguiente);
	}

}
