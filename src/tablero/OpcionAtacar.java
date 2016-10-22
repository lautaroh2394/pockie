package tablero;

import enums.OpcionHabilitada;
import enums.StateMenu;

public class OpcionAtacar extends OpcionMenuNinja{

	public OpcionAtacar(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY, MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Atacar";
		
		if  (!m.getN().getBanderaAtt()) this.enabled = OpcionHabilitada.enabled;
		else this.enabled = OpcionHabilitada.disabled;
	}
	
	@Override
	public boolean queHacer(int mx, int my, Cuadro c) {
		if (this.meClickearon(mx,my)){
		//MenuNinja.estadoMenu = StateMenu.SeleccionCuadroAtacarNinja;
		Tablero.stateMenu = StateMenu.SeleccionCuadroAtacarNinja;
		c.getT().colorearCercanosAtt(c.getNinja());
		return true;
		} else return false;
	}

}
