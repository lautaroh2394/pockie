package tablero;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import enums.IDEquipo;
import enums.StateMenu;
import main.GameObject;
import ninjas.EquipoNinja;
import ninjas.Ninja;

public class Tablero extends GameObject {
	
	private EquipoNinja equipoA;
	private EquipoNinja equipoB;
	
	private LinkedList<Cuadro> cuadros;
	
	private int cantCuadrosEnY;
	private int cantCuadrosEnX;
	
	private int hPantalla;
	private int wPantalla;
	
	private Cuadro cuadroActivo;
	
	private Color colorNoP = Color.WHITE;
	private Color colorPA = Color.red;
	private Color colorPM = Color.yellow;
	
	public Tablero(int hc, int wc, int hPant, int wPant){
		this.cantCuadrosEnY = hc;
		this.cantCuadrosEnX = wc;
		this.hPantalla = hPant;
		this.wPantalla = wPant;
		
		this.equipoA = new EquipoNinja(IDEquipo.A);
		this.equipoB = new EquipoNinja(IDEquipo.B);
		
		this.cuadros = new LinkedList<Cuadro>();
		this.initCuadros();
	}
	
	public void addNinja(Ninja ninja, IDEquipo id){

		if (id == IDEquipo.A){ equipoA.addNinja(ninja);}
		else equipoB.addNinja(ninja);
	}
	
	public void removeNinja(Ninja ninja, IDEquipo id){

		if (id == IDEquipo.A){ equipoA.removeNinja(ninja);}
		else equipoB.removeNinja(ninja);
	}

	@Override
	public void tick() {

		equipoA.tick();
		equipoB.tick();
		
	}

	public LinkedList<Cuadro> getCuadros() {
		return cuadros;
	}

	public void setCuadros(LinkedList<Cuadro> cuadros) {
		this.cuadros = cuadros;
	}

	@Override
	public void render(Graphics g) {
		
		this.dibujarTablero(g);

		equipoA.render(g);
		equipoB.render(g);
		
	}

	private void dibujarTablero(Graphics g){
		
		for(Cuadro c : cuadros){
			c.render(g);
		}
	}
	
	private void initCuadros(){
		
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
	
	public void cuadroClickeadoMenuNinja(int mx, int my){
		for(Cuadro c : cuadros){
			
				if (c.seClickeo(mx,my)){
					if (MenuNinja.estadoMenu != StateMenu.NoInstanciado) {
						if (c.noTieneMenu()){
							buscarCuadroConMenuYTogglear();
						}
						else {
							c.getNinja().getMenu().decidiQueHacer(mx, my, c);
							this.cuadroActivo = c;							
						}
				}
					else {
					c.toggleMenu();
					}
				}
			}
	}
	
	
	
	private void buscarCuadroConMenuYTogglear(){
		for(Cuadro c : cuadros){
			if (!c.ninjaIsNull()){
				if (!c.getNinja().menuIsNull()){
					c.toggleMenu();
				}
			}
		}
	}
	
	public void seleccionCuadroMov(int mx, int my){
		Cuadro c = cuadroEnPos(mx, my);
		
		if (c != null) {
			if (this.cuadroActivo.getNinja().movete(c)){
			this.cuadroActivo = null;
			}
		} else this.cuadroActivo.toggleMenu();;
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
	
	public void colorearCercanosMov(Ninja n){
		for (Cuadro c : cuadros){
			if (distancia(c,n.getCuadro())<=n.getDistMov()){
				c.setColor(colorPM);
			}
		}
	}
	
	public void colorearCercanosAtt(Ninja n){
		for (Cuadro c : cuadros){
			if (distancia(c,n.getCuadro())<=n.getDistAtt()){
				c.setColor(colorPA);
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
	
	public EquipoNinja getEquipoA() {
		return equipoA;
	}

	public void setEquipoA(EquipoNinja equipoA) {
		this.equipoA = equipoA;
	}

	public EquipoNinja getEquipoB() {
		return equipoB;
	}

	public void setEquipoB(EquipoNinja equipoB) {
		this.equipoB = equipoB;
	}

	
}
