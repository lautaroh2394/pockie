package ninjas;

import java.awt.Graphics;
import java.util.LinkedList;

import enums.IDEquipo;

public class EquipoNinja {
	//TODO: POCKIE Hacer que miembros de un mismo equipo no se puedan atacar (no necesariamente en esta clase)
	//TODO: POCKIE Hacer que puedan atacar los del equipo activo solamente, cuando terminen todos, el otro equipo pasa a estar activo.
	//TODO: POCKIE Hacer que cada ninja despues de atacar ya no pueda hacer nada, por mas que no se haya movido
	public LinkedList<Ninja> ninjas;
	public IDEquipo id;
	
	public EquipoNinja(IDEquipo id, LinkedList<Ninja> n){
		this.ninjas = n;
		this.id= id;
	}
	
	public EquipoNinja(IDEquipo id){
		this(id, new LinkedList<Ninja>());
	}
	
	public void tick(){
		for (Ninja n : ninjas){
			chequearSiMurio(n);
			n.tick();
		}
	}
	
	private void chequearSiMurio(Ninja n){
		if (n.chequearSiMurio()) {
			murio(n);
		}
	}
	
	private void murio(Ninja n){
		n.getCuadro().ninjaSeFue();
		ninjas.remove(n);
	}
	
	public void render(Graphics g){
		for (Ninja n : ninjas){
			n.render(g);
		}
	}
	
	public float getVidaActual(){
		float vidaActual = 0;
		for (Ninja n : ninjas){
			vidaActual+= n.getVida();
		}
		
		return vidaActual;
	}
	
	public float getVidaTotal(){
		float vidaTotal = 0;
		for (Ninja n : ninjas){
			vidaTotal += n.getVidaMax();
		}
		
		return vidaTotal;
	}
	
	public void addNinja(Ninja n){
		n.idequipo = this.id;
		this.ninjas.add(n);
	}
	
	public void removeNinja(Ninja n){
		this.ninjas.remove(n);
	}

}
