package ninjas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import enums.IDEquipo;
import tablero.Tablero;

public class EquipoNinja {
	//TODO: POCKIE Hacer que un ninja se pueda mover pero dependiendo de obstaculos qeu tenga, no los puede saltar
	public LinkedList<Ninja> ninjas;
	public IDEquipo id;
	
	public EquipoNinja(IDEquipo id, LinkedList<Ninja> n){
		this.ninjas = n;
		this.id= id;
		avisarlesDeQueEquipoSon(n);
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
	
	private void avisarlesDeQueEquipoSon(LinkedList<Ninja> ninjas){
		if (ninjas!=null){
		for (Ninja n : ninjas){
			n.setIdequipo(this.id);
		}
		}
	}
	
	public void IA(Tablero tab){
		for (Ninja n : ninjas){
			n.IA(tab);
		}
	}
	
	private void chequearSiMurio(Ninja n){
		if (n.chequearSiMurio()) {
			murio(n);
		}
	}
	
	public Ninja cercanoA(Ninja nin){
		
		Ninja masCercano = ninjas.get(0);
		
		for (Ninja n : ninjas){
			if (n.distancia(n.getCuadro(), nin.getCuadro(), masCercano.distA(nin))){
				masCercano = n;
			}
		}
		
		return masCercano;
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
		n.setIdequipo(id);
	}
	
	public void removeNinja(Ninja n){
		this.ninjas.remove(n);
	}
	
	public boolean esFinDeTurno(){
		
		boolean rta = true;
		
		for (int i = 0; i<ninjas.size();i++){
			if (ninjas.get(i).puedeHacerAlgo()){
				rta = false;
				i = ninjas.size();
			}
		}
		if (rta) {
			resetBanderasMovYAtt();
		}
		
		return rta;
	}
	
	private void resetBanderasMovYAtt(){
		for (Ninja n : ninjas){
			n.resetBandMyA();
		}
	}
	
	public void setColorNombre(Color c){
		for (Ninja  n : ninjas){
			n.setNombreNormal(c);
		}
	}

}
