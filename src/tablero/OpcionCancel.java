package tablero;

import enums.OpcionHabilitada;

public class OpcionCancel extends OpcionMenuNinja{

	public OpcionCancel(int posOpcionCX, int posOpcionCY, int anchoOpcionC, int altoOpcionC, int posSX, int posSY, MenuNinja m) {
		super(posOpcionCX, posOpcionCY, anchoOpcionC, altoOpcionC, posSX, posSY, m);
		texto = "Cancel";
		this.enabled = OpcionHabilitada.enabled;
	}
	
	@Override
	public boolean queHacer(int mx, int my, Cuadro c) {//nunca llama a este metodo, cuando clickea en cancelar en MouseEventsFight 
		if (this.meClickearon(mx,my)){
		this.menu.getN().toggleMenu();
		return true;
		} else return false;
	}

}
