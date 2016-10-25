package ninjas;

import java.util.LinkedList;

import enums.IDEquipo;
import ia.IA;

public class EquipoAI extends EquipoNinja {
	
	private IA ia;

	public EquipoAI(IDEquipo id, LinkedList<Ninja> n){
		super(id,n);

	}
	public EquipoAI(IDEquipo id){
		this(id, new LinkedList<Ninja>());
	}
	
	public EquipoAI(IDEquipo id, LinkedList<Ninja> n, IA ia){
		super(id,n);
		this.ia = ia;

	}
	
	@Override
	public void tick(){
			for (Ninja n : ninjas){
				n.tick();
				}
			IA();
		}
	
	@Override
	public void addNinja(Ninja n){
		super.addNinja(n);
	}
	
	private synchronized void IA(){
		if (ninjaActivo== null) ninjaActivo = ninjas.get(0);
		ia.accionAutomatica(ninjaActivo, ninjaActivo.getCuadro().getT());
		
		if (!ninjaActivo.puedeHacerAlgo()) {
			setSiguienteActivo();
		}
	}
	
	private void setSiguienteActivo(){
		int indexsiguiente = (ninjas.indexOf(ninjaActivo)+1)% ninjas.size();
		ninjaActivo = ninjas.get(indexsiguiente);
	}
	

	public void setIA(IA ia){
		this.ia = ia;
	}

}
