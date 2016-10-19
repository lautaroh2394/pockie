package tablero;

import enums.OpcionHabilitada;
import enums.StateMenu;

public class OpcionMover extends OpcionMenuNinja{

	public OpcionMover(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY, MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Mover";
		
		if (!m.getN().getBanderaMov()) {this.enabled = OpcionHabilitada.enabled;}
		else this.enabled = OpcionHabilitada.disabled;
	}

	@Override
	public boolean queHacer(int mx, int my, Cuadro c) {
		if (this.meClickearon(mx,my)){
		MenuNinja.estadoMenu = StateMenu.SeleccionCuadroMoverNinja;
		c.getT().colorearCercanosMov(c.getNinja());
		return true;
		} else return false;
	}
	
	

}
