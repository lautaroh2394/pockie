package tablero;

import java.awt.Color;

import enums.OpcionHabilitada;
import enums.StateMenu;
import ninjas.Ninja;

public class OpcionAtacar extends OpcionMenuNinja{
	
	private Color colorPA = Color.red;

	public OpcionAtacar(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY, MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Atacar";
		
		if  (!m.getN().getBanderaAtt()) this.enabled = OpcionHabilitada.enabled;
		else this.enabled = OpcionHabilitada.disabled;
	}
	
	@Override
	public boolean queHacer(int mx, int my, Ninja n) {
		if (this.meClickearon(mx,my)){
		Tablero.stateMenu = StateMenu.SeleccionCuadroAtacarNinja;
		n.pediQueTeColoreenLosCercanos(n.getDistAtt(), colorPA);
		return true;
		} else return false;
	}

}
