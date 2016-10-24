package tablero;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import enums.IDEquipo;
import enums.StateMenu;
import main.GameObject;
import ninjas.EquipoNinja;
import ninjas.Ninja;

public class Tablero extends GameObject {
	
	public static StateMenu stateMenu = StateMenu.NoInstanciado; 
	
	private LinkedList<EquipoNinja> equipos;
	private int equipoActivo = -1;
	
	private LinkedList<Cuadro> cuadros;
	private Cuadro cuadroActivo;
	
	private int cantCuadrosEnY;
	private int cantCuadrosEnX;	
	
	private int hPantalla;
	private int wPantalla;
	
	private HUD hud;
	
	private Color colorNoP = Color.WHITE;
	
	private boolean finDeJuego = false;
	
	public Tablero(int hc, int wc, int hPant, int wPant){
		
		setUp(hc, wc, hPant, wPant);
//		agregarEquipos();
		initCuadros();
		hud = new HUD(hPant,wPant);
		
	}
	
	private void setUp(int hc,int wc,int hPant,int wPant){
		this.cantCuadrosEnY = hc;
		this.cantCuadrosEnX = wc;
		this.hPantalla = hPant;
		this.wPantalla = wPant;
	}
	
	private void agregarEquipos(LinkedList<EquipoNinja> e){
		this.equipos = new LinkedList<EquipoNinja>();
		
		for (EquipoNinja eq : e){
		equipos.add(eq);
		
		}
		equipos.get(0).toggleActivacion();
		setReferenciaAEquipoActivo(0);
		
	}
	
	private void setReferenciaAEquipoActivo(int i){
		equipoActivo = i;
	}
	
	public void addNinja(Ninja ninja, IDEquipo id){
		
		for (int i = 0 ; i< equipos.size();i++){
			if (equipos.get(i).id == id){
				equipos.get(i).addNinja(ninja);
				i = equipos.size();
			}
		}
	}
	
	public void removeNinja(Ninja ninja, IDEquipo id){
		
		for (int i = 0 ; i< equipos.size();i++){
			if (equipos.get(i).id == id){
				equipos.get(i).removeNinja(ninja);
				i = equipos.size();
			}
		}
	}

	@Override
	public void tick() {
		if (!finDeJuego){
		equiposTick();
		chequearFinDeTurno();
		chequearFinJuego();
		}
	}

	@Override
	public void render(Graphics g) {
		
		this.dibujarTablero(g);		
		renderEquipos(g);
		hud.render(g);
		
		if (finDeJuego){
			renderMjeFinJuego(g);
		}
	}

	private void renderEquipos(Graphics g){
		for (EquipoNinja en : equipos){
			en.render(g);
		}
	}
	
	private void renderMjeFinJuego(Graphics g){
		
		int x = (int) (this.hPantalla*0.40);
		int y = (int) (this.wPantalla*0.45);
		
		g.setFont(new Font(null, 10, 80 ));
		if (getUnicoVivo().getId()== IDEquipo.CPU){
			g.setColor(new Color(181, 12, 130));
			g.drawString("Derrota", x, y);
		}
		else {
			g.drawString("Victoria.", x, y);
		}
	}
	
	private void dibujarTablero(Graphics g){
		
		for(Cuadro c : cuadros){
			c.render(g);
		}
	}
	
	private void initCuadros(){
		
		this.cuadros = new LinkedList<Cuadro>();
		
		int xrelTab = 0;
		int yrelTab = 0;
		
		int anchoCadaCuadro = wPantalla/(cantCuadrosEnX+2);
		int altoCadaCuadro = hPantalla/(cantCuadrosEnY+2);
		
		int posCuadroX = (wPantalla - cantCuadrosEnX*anchoCadaCuadro - (2*(cantCuadrosEnX-1)))/2;
		int posCuadroY = (hPantalla - cantCuadrosEnY*altoCadaCuadro - (2*(cantCuadrosEnY-1)))/2;
		
		
		for(int i = 0;i<cantCuadrosEnY;i++){
			
			for (int j = 0; j<cantCuadrosEnX; j++){
				
				Cuadro temp = new Cuadro(Color.WHITE,altoCadaCuadro,anchoCadaCuadro,posCuadroX,posCuadroY,this);
				cuadros.add(temp);
				
				temp.setPosRelX(xrelTab);
				xrelTab++;
				temp.setPosRelY(yrelTab);
				
				posCuadroX+= anchoCadaCuadro + 2;
			}
			
			posCuadroY += altoCadaCuadro + 2;
			posCuadroX =(wPantalla - cantCuadrosEnX*anchoCadaCuadro - (2*(cantCuadrosEnX-1)))/2;
			
			xrelTab = 0;
			yrelTab++;
		}
	}
	
	public void eventoClick(int mx, int my){
		
		boolean rta = getEquipoActivo(equipoActivo).esTuClick(mx, my) ;
		
		if (rta) {
			cuadroActivo = cuadroEnPos(mx, my);
		}
		else {
			getEquipoActivo(equipoActivo).togglearMenuInstanciado();
		}
	}
		
	private EquipoNinja getEquipoActivo(){
		
		if (equipoActivo != -1) {
			return getEquipoActivo(equipoActivo) ;
		}
		else{
		EquipoNinja eq = null;
		for (int i = 0; i< equipos.size();i++){
			if (equipos.get(i).getActivo()){
				eq = equipos.get(i);
				i = equipos.size();
			}
		}
		setReferenciaAEquipoActivo(equipos.indexOf(eq));
		return eq;
		}
	}
	
	private EquipoNinja getEquipoActivo(int equipo){
		return equipos.get(equipo);
	}
	
	public void seleccionCuadroMov(int mx, int my){
		Cuadro c = cuadroEnPos(mx, my);

		if (c != null) {
			if (this.cuadroActivo.getNinja().movete(c)){
				this.cuadroActivo = null;
			}
		}else this.cuadroActivo.toggleMenu();
		resetColores();
	}
	
	public void seleccionCuadroAtt(int mx, int my){
		Cuadro c = cuadroEnPos(mx, my);
		
		if (c != null) {
			if (this.cuadroActivo.getNinja().atacaA(c)){
				this.cuadroActivo = null;
			}
		}else this.cuadroActivo.toggleMenu();
		resetColores();
	}
	
	private Cuadro cuadroEnPos(int mx, int my){
		Cuadro c = null;
		for (int i = 0; i< cuadros.size();i++){
			if (cuadros.get(i).seClickeo(mx, my)){
				c = cuadros.get(i);
				i = cuadros.size();
			}
		}
		return c;
	}
	
	public void colorearCercanos(Cuadro cuadro, int nro, Color color ){
		for (Cuadro c : cuadros){
			if (distancia(c,cuadro)<=nro){
				c.setColor(color);
			}
		}
	}
	
	private void resetColores(){
		for (Cuadro c : cuadros){
			if (c.getColor() != colorNoP){
				c.cambiaColor();
			}
		}
	}
	
	private void equiposTick(){
		
		for (EquipoNinja e : equipos){
			e.controlarMuertos();
		}
		
		getEquipoActivo().tick();
	}
		
	private void chequearFinDeTurno(){		
		
		if (getEquipoActivo().esFinDeTurno()){
			toggleEquipoActivo();
		}
	}
	
	private void chequearFinJuego() {
		for (int i = 0;i< equipos.size();i++){
			if (!equipos.get(i).teMoriste()){
				if (unicoVivo(equipos.get(i).getId())) {
					finDeJuego = true;
					i = equipos.size();
				}
			}
		}
		
	}
	
	private EquipoNinja getUnicoVivo(){
		EquipoNinja e = null;
		for (EquipoNinja eq : equipos){
			if (unicoVivo(eq.getId())){ e = eq; return eq;}
		}
		return e;
	}
	
	public Ninja buscarEnemigoCercano(Ninja n){
		
		//se supone que lo llama siempre un equipo activo
		int menorDist = 500;
		Ninja cercano = null;
		Ninja temp = null;
		
		
		for (EquipoNinja e : equipos){
			temp = e.cercanoA(n);
			if (n.distA(temp)<menorDist && e.getId()!=n.getIdEquipo()){
				
				menorDist = n.distA(temp);
				cercano = temp;
				
			}
		}
		return cercano;		
	}
	
	public boolean unicoVivo(IDEquipo id){
		for (EquipoNinja eq : equipos){
			if (eq.id != id && eq.ninjas.size() != 0){
				return false;
			}
		}
		return true;
	}
	
	private void toggleEquipoActivo(){

		int actual = equipos.indexOf(getEquipoActivo());
		
		actual = (actual+1) % (equipos.size());
		equipoActivo = actual;
		equipos.get(equipoActivo).toggleActivacion();
		
		
	}
	
	private int distancia(Cuadro c1, Cuadro c2){
		return (distX(c1,c2)+distY(c1,c2));
	}
	
	private int distX(Cuadro c1, Cuadro c2){
		return Math.abs(posCol(c1)-posCol(c2));
	}
	
	private int distY(Cuadro c1, Cuadro c2){
		return Math.abs(posFila(c1)-posFila(c2));
	}
	
	private int posFila(Cuadro c){
		return c.getPosRelY();
	}
	
	private int posCol(Cuadro c){
		return c.getPosRelX();
	}
	
	public int getPosDeCuadroX(int n){
		return cuadros.get(n).getX();
	}
		
	public int getPosDeCuadroY(int n){
		return cuadros.get(9*n).getY();
	}
	
	public int getCantCuadrosEnY() {
		return cantCuadrosEnY;
	}

	public void setCantCuadrosEnY(int cantCuadrosEnY) {
		this.cantCuadrosEnY = cantCuadrosEnY;
	}

	public int getCantCuadrosEnX() {
		return cantCuadrosEnX;
	}

	public void setCantCuadrosEnX(int cantCuadrosEnX) {
		this.cantCuadrosEnX = cantCuadrosEnX;
	}
		
	public Cuadro getCuadroActivo(){
		return this.cuadroActivo;
	}
	
	public LinkedList<Cuadro> getCuadros() {
		return cuadros;
	}

	public void setCuadros(LinkedList<Cuadro> cuadros) {
		this.cuadros = cuadros;
	}
	
	public void setEquipos(LinkedList<EquipoNinja> e){
		this.agregarEquipos(e);
		this.hud.setEquipos(e);
	}
	
	public LinkedList<EquipoNinja> getEquipos(){
		return this.equipos;
	}
	
}
