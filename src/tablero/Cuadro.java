package tablero;

import java.awt.Color;
import java.awt.Graphics;

import enums.IDEquipo;
import ninjas.Ninja;

public class Cuadro {
	
	private Color color;
	private Color colorNoPintado;
	private int height;
	private int width;
	
	private int x;
	private int y;
	
	private int posRelativaATableroX;
	private int posRelativaATableroY;
	
	private Tablero t;
	
	private Ninja ninja;
	
	public Cuadro(Color c, int h, int w, int x, int y, Tablero t){
		this.x=x;
		this.y=y;
		this.color=c;
		this.colorNoPintado = c;
		this.height=h;
		this.width=w;
		this.t= t;
	}
	
	public void render(Graphics g){
		g.setColor(this.color);
		g.drawRect(x, y, width, height);
	}
	
	public boolean seClickeo(int mx, int my){
		
		boolean bandera = false;
		if ( (mx > this.getX() ) && (mx< this.getX()+this.getWidth()) ){
			if ((my > this.getY() ) && (my< this.getY()+ this.getHeight())){
				bandera = true;
			}
		}
		return bandera;
		
	}
	
	public void cambiaColor(){
		setColor(colorNoPintado);		
	}
	
	public void toggleMenu(){
		if (puedeHacerAlgo())
			this.ninja.toggleMenu();
	}
	
	private boolean puedeHacerAlgo(){
		return (!ninjaIsNull() && getNinja().puedeHacerAlgo());
	}
	
	public void ninjaSeFue(){
		this.ninja =null;
	}
	
	public boolean noTieneMenu(){
		if (ninjaIsNull()) { return true;}
		else return this.getNinja().menuIsNull();
	}
	
	public void atacaronATuNinja(float danio){
		
		if (!ninjaIsNull()){
			getNinja().teAtacaron(danio);
		}
		
	}
	
	public void ocultarMenu(){
		if (!getNinja().menuIsNull()){ 
		getNinja().getMenu().ocultarMenu();
		}
	}
	
	
	public void setPosRelX(int x){
		this.posRelativaATableroX = x;
	}
	
	public void setPosRelY(int y){
		this.posRelativaATableroY = y;
	}
	
	public int getPosRelX(){
		return this.posRelativaATableroX;
	}
	
	public int getPosRelY(){
		return this.posRelativaATableroY;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColorNoPintado() {
		return colorNoPintado;
	}

	public void setColorNoPintado(Color colorNoPintado) {
		this.colorNoPintado = colorNoPintado;
	}
	
	public void setNinja(Ninja nin){
		if (this.ninja == null){
			this.ninja = nin; 
		}
	}
	
	public Ninja getNinja(){
		return this.ninja;
	}
	
	public boolean ninjaIsNull(){
		return (null == this.getNinja());
	}
	
	public Tablero getT() {
		return t;
	}


}
