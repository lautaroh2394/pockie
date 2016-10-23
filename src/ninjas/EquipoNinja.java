package ninjas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import enums.IDEquipo;
import enums.StateMenu;
import tablero.Cuadro;
import tablero.Tablero;

public class EquipoNinja {
	//TODO: POCKIE Hacer que un ninja se pueda mover pero dependiendo de obstaculos qeu tenga, no los puede saltar
	public LinkedList<Ninja> ninjas;
	public IDEquipo id;
	
	protected boolean activo = false;
	protected Ninja ninjaActivo;
	
	protected float vidaMax = 0;
	
	public EquipoNinja(IDEquipo id, LinkedList<Ninja> n){
		this.ninjas = n;
		this.id= id;
		avisarlesDeQueEquipoSon(n);
	}
	
	public EquipoNinja(IDEquipo id){
		this(id, new LinkedList<Ninja>());
	}
	
	public void tick(){ //TODO: arreglar los problemas de concurrencia (concurrentmodificationexception)
						//ver lo de como solucionar usando iterator
//	for (Ninja n : ninjas){
//		n.tick();
//		chequearSiMurio(n);
//		
//	}
		
		for (int i = 0; i< ninjas.size(); i++){
			ninjas.get(i).tick();
			chequearSiMurio(ninjas.get(i));

		}
}
	
	protected void avisarlesDeQueEquipoSon(LinkedList<Ninja> ninjas){
		if (ninjas!=null){
		for (Ninja n : ninjas){
			n.setIdequipo(this.id);
			vidaMax+=n.getVidaMax();
		}
		}
	}
	
	protected void chequearSiMurio(Ninja n){
		if (n.chequearSiMurio()) {
			murio(n);
		}
	}
	
	public Ninja cercanoA(Ninja nin){
		if (ninjas.size() > 0){
		
		Ninja masCercano = ninjas.getFirst();
		for (Ninja n : ninjas){
			if (n.distancia(n.getCuadro(), nin.getCuadro(), masCercano.distA(nin))){
				masCercano = n;
			}
		}
		return masCercano;
		}
		return null;
	}
	
	protected void murio(Ninja n){
		n.getCuadro().ninjaSeFue();
		ninjas.remove(n);
//		n.moriste();
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
		return vidaMax;
	}
	
	public void addNinja(Ninja n){
		n.idequipo = this.id;
		this.ninjas.add(n);
		n.setIdequipo(id);
		vidaMax+= n.getVidaMax();
	}
	
	public void removeNinja(Ninja n){
		this.ninjas.remove(n);
	}
	
	public boolean esTuClick(int mx, int my){
		
		boolean rta = false;
		for(Ninja n : ninjas){
			
			if (n.seClickeo(mx, my)){
				
				rta = true;
				
				if (n.seClickeoMenu(mx,my)){
					n.decidiQueHacer(mx,my);
					n.ocultarMenu();

				}
				
				else if (n.seClickeoCuadro(mx,my)){
					if (menuEstaInstanciado()) {
						if (n.menuIsNull()){
							ninjaActivo.toggleMenu();
							ninjaActivo = n;
							
						}
						else {
							n.toggleMenu();
							ninjaActivo = n;
							
						}
					}
					else {
						n.toggleMenu();
						ninjaActivo = n;
						
					}
				}
			}
		}
		return rta;
		
		
	}
	
	public void togglearMenuInstanciado(){
		for (int i = 0 ; i<ninjas.size();i++){
			if (!ninjas.get(i).menuIsNull()){
				ninjas.get(i).toggleMenu();
				i= ninjas.size();
			}
		}
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
		}
		
		return rta;
	}
	
	protected void resetBanderasMovYAtt(){
		for (Ninja n : ninjas){
			n.resetBandMyA();
		}
	}
	
	public boolean teMoriste(){
		return (getVidaActual() == 0);
	}
	
	public void setColorNombre(Color c){
		for (Ninja  n : ninjas){
			n.setNombreNormal(c);
		}
	}
	
	public boolean menuEstaInstanciado(){
		boolean rta = false;
		
		for (int i = 0; i< ninjas.size(); i++){
			if (!ninjas.get(i).menuIsNull()){
				rta = true;
				i = ninjas.size();
			}
		}
		return rta;
	}
	
	public void toggleActivacion(){
		activo = !activo;
		resetBanderasMovYAtt();
		resetMenus();
	}
	
	protected void resetMenus(){
		for (Ninja n : ninjas){
			n.resetMenu();
		}
	}
	
	public IDEquipo getId(){
		return this.id;
	}
	
	public boolean getActivo(){
		return activo;
	}

}
