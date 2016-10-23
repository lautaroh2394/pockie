package tablero;

import java.awt.Color;

import enums.OpcionHabilitada;
import enums.StateMenu;
import ninjas.Ninja;

public class OpcionMover extends OpcionMenuNinja{
	
	private Color colorPM = Color.yellow;

	public OpcionMover(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY, MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Mover";
		
		if (!m.getN().getBanderaMov()) {this.enabled = OpcionHabilitada.enabled;}
		else this.enabled = OpcionHabilitada.disabled;
	}

	@Override
	public boolean queHacer(int mx, int my, Ninja n) {
		if (this.meClickearon(mx,my)){
		Tablero.stateMenu = StateMenu.SeleccionCuadroMoverNinja;
		n.pediQueTeColoreenLosCercanos(n.getDistMov(), colorPM);
		
		return true;
		} else return false;
	}
	
	

}
