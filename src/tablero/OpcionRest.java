package tablero;

import enums.OpcionHabilitada;
import enums.StateMenu;

public class OpcionRest extends OpcionMenuNinja{

	public OpcionRest(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY,
			MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Rest";
		
		this.enabled = OpcionHabilitada.enabled;
	}

	@Override
	public boolean queHacer(int mx, int my, Cuadro c) {
		if (this.meClickearon(mx, my)){
			
			Tablero.stateMenu = StateMenu.NoInstanciado;
			c.getNinja().rest();
			
			return true;
		} else return false;
	}

}
