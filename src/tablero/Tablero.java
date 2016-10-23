package tablero;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;

import enums.IDEquipo;
import enums.StateMenu;
import main.GameObject;
import ninjas.EquipoNinja;
import ninjas.Ninja;

public class Tablero extends GameObject {
	
	//TODO: ia del equipo malo, aunque sea chota, que se muevan solos. esta implementado algo pero estoy cansado y cero ganas de ver porqeu
	
	public static StateMenu stateMenu = StateMenu.NoInstanciado; 
	
	private LinkedList<EquipoNinja> equipos;
	private int equipoActivo;
	
	private LinkedList<Cuadro> cuadros;
	private Cuadro cuadroActivo;
	
	private int cantCuadrosEnY;
	private int cantCuadrosEnX;	
	
	private int hPantalla;
	private int wPantalla;
	
	
	
	private Color colorNoP = Color.WHITE;
	
	public Tablero(int hc, int wc, int hPant, int wPant){
		
		setUp(hc, wc, hPant, wPant);
		agregarEquipos();
		initCuadros();
		
	}
	
	private void setUp(int hc,int wc,int hPant,int wPant){
		this.cantCuadrosEnY = hc;
		this.cantCuadrosEnX = wc;
		this.hPantalla = hPant;
		this.wPantalla = wPant;
	}
	
	private void agregarEquipos(){
		this.equipos = new LinkedList<EquipoNinja>();
		EquipoNinja a = new EquipoNinja(IDEquipo.A); a.toggleActivacion();
		equipos.add(a); equipos.add(new EquipoNinja(IDEquipo.B));
		
		setReferenciaAEquipoActivo();
	}
	
	private void setReferenciaAEquipoActivo(){
		equipoActivo = equipos.indexOf(getEquipoActivo());
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

		equiposTick();
		chequearFinDeTurno();
	}

	@Override
	public void render(Graphics g) {
		
		this.dibujarTablero(g);
		
		renderEquipos(g);
	}

	private void renderEquipos(Graphics g){
		for (EquipoNinja en : equipos){
			en.render(g);
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
		
		EquipoNinja eq = null;
		for (int i = 0; i< equipos.size();i++){
			if (equipos.get(i).getActivo()){
				eq = equipos.get(i);
				i = equipos.size();
			}
		}
		return eq;
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
		
		for (EquipoNinja en : equipos){
			en.tick();
		}
	}
	
	private void chequearFinDeTurno(){		
		
		if (getEquipoActivo(equipoActivo).esFinDeTurno()){
			toggleEquipoActivo();
		}
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
	
	private void toggleEquipoActivo(){

		int actual = equipos.indexOf(getEquipoActivo(equipoActivo));
		
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
		this.equipos = e;
	}
	
}
