package tablero;

import enums.OpcionHabilitada;
import enums.StateMenu;
import ninjas.Ninja;

public class OpcionRest extends OpcionMenuNinja{

	public OpcionRest(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY,
			MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Rest";
		
		this.enabled = OpcionHabilitada.enabled;
	}

	@Override
	public boolean queHacer(int mx, int my, Ninja n) {
		if (this.meClickearon(mx, my)){
			
			Tablero.stateMenu = StateMenu.NoInstanciado;
			n.rest();
			
			return true;
		} else return false;
	}

}
