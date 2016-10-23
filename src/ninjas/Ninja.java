package ninjas;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import enums.IDEquipo;
import enums.StateMenu;
import tablero.Cuadro;
import tablero.MenuNinja;
import tablero.Tablero;

public class Ninja {

	public String nom;
	private float att;
	private float def;
	private int tam;
	private float vida;
	private float vidaMax;

	private int distMov;
	private int distAtt;

	public Color colorNormal;
	public Color nombreInhabilitado = Color.cyan;
	public Color nombreNormal = Color.magenta;

	private int x;
	private int y;

	private Cuadro cuadro;

	public boolean banderaMov = false;
	public boolean banderaAtt = false;
	private MenuNinja menu;

	public IDEquipo idequipo;

	public Ninja(String nombre, float ataque, float defensa, Cuadro cuadro, int t) {
		this.nom = nombre;
		this.att = ataque;
		this.def = defensa;
		this.vida = this.vidaMax = 5 * defensa;
		setXEnCuadro(cuadro, t);
		setYEnCuadro(cuadro, t);
		this.tam = t;
		this.cuadro = cuadro;
		cuadro.setNinja(this);
		this.distAtt = 3;
		this.distMov = 4;
	}

	public Ninja(String nombre, Cuadro cuadro, int t) {
		this(nombre, 1000, 1000, cuadro, t);
	}

	public Ninja(String nombre, Cuadro cuadro, int t, Color n) {
		this(nombre, 1000, 1000, cuadro, t);
		this.colorNormal = n;
	}

	public void tick() {
		// restarVidaPrueba();
	}

	public void toggleMenu() {
		if (this.idequipo != IDEquipo.CPU) {
			if (this.menu == null) {
				this.menu = new MenuNinja(this);
			} else {
				Tablero.stateMenu = StateMenu.NoInstanciado;
				this.menu = null;
			}
			;
		}
	}

	public void render(Graphics g) {

		renderCuerpo(g);
		renderNombre(g);
		renderVida(g);

		renderMenu(g);
	}

	private void renderMenu(Graphics g) {
		if (this.menu != null)
			this.menu.render(g);
	}

	private void renderNombre(Graphics g) {
		if (!puedeHacerAlgo()) {
			g.setColor(nombreInhabilitado);
		} else
			g.setColor(nombreNormal);

		g.drawString(this.nom, (int) (cuadro.getX() + cuadro.getWidth() * 0.15),
				(int) (cuadro.getY() + cuadro.getHeight() * 0.26));
	}

	public void IA() {	//hay que refinar, pero la base base esta
						//TODO: arreglar la hiper velocidad con la que atacan y se mueven solos 
		int timer =0 ;
		boolean b=false;
		
		while (!b){
			
		timer++;
		Tablero t = getCuadro().getT();
		Ninja e = null;
		if( !elOtroEquipoEstaMuerto(this.idequipo)){
			e = t.buscarEnemigoCercano(this);
		}
		if (!banderaMov && timer>=500){
 
			if (e != null)
				{
				moverseParaAtacar(e, t);
				}
		 
		 timer = 0;
		 }
		if (!banderaAtt&&timer>=500){
			if (puedeAtacar(e))
			{
				atacaA(e.getCuadro());
				timer =0;
				}
			else rest();
			}
		if (!this.puedeHacerAlgo()){ b= true;}
		}
	}
	
	private boolean elOtroEquipoEstaMuerto(IDEquipo id){
		return getCuadro().getT().unicoVivo(id);
	}
	
	private boolean puedeAtacar(Ninja c){
		if (c != null){
		return distancia(c.getCuadro(),this.cuadro,this.distAtt);} else return false;
	}

	private void moverseParaAtacar(Ninja n, Tablero t) {
		Random r = new Random();
		Cuadro temp;
		boolean b = true;
		LinkedList<Cuadro> cquemepuedomover = new LinkedList<Cuadro>();
		LinkedList<Cuadro> cquepuedoAtacarsimemuevoono = new LinkedList<Cuadro>();
		for (Cuadro c : t.getCuadros()){
			if (puedoMovermeA(c)){
				cquemepuedomover.add(c);
			}
			if (distancia(n.getCuadro(),c,this.distAtt) && puedoMovermeA(c)){
				cquepuedoAtacarsimemuevoono.add(c);
			}
		}
		
		if (cquepuedoAtacarsimemuevoono.size() != 0){
			movete(cquepuedoAtacarsimemuevoono.get(r.nextInt(cquepuedoAtacarsimemuevoono.size())));
		}
		else if (cquemepuedomover.size() != 0){
			movete(cquemepuedomover.get(r.nextInt(cquemepuedomover.size())));
		}
		else this.rest();
	}

	private boolean puedoMovermeA(Cuadro cuadro) {
		return (distancia(this.cuadro, cuadro, this.distMov) && !banderaMov && cuadro != this.cuadro
				&& cuadro.ninjaIsNull());
	}

	public boolean puedeHacerAlgo() {
		return !(banderaAtt && banderaMov);
	}

	private void renderCuerpo(Graphics g) {
		g.setColor(this.colorNormal);
		g.fillRect(x, y, tam, tam);
	}

	private void renderVida(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int) (cuadro.getX() + 2), (int) (cuadro.getY() + cuadro.getHeight() * 0.03),
				(int) ((cuadro.getWidth() - 4) * (vida) / vidaMax), 5);
	}

	public boolean movete(Cuadro cuadro) {
		if (puedoMovermeA(cuadro)) {
			setXEnCuadro(cuadro, this.tam);
			setYEnCuadro(cuadro, this.tam);
			this.cuadro.ninjaSeFue();
			this.setCuadro(cuadro);
			cuadro.setNinja(this);

			this.toggleMenu();
			meMovi();

			return true;
		} else {
			this.toggleMenu();
			return false;
		}
	}

	public boolean distancia(Cuadro c1, Cuadro c2, int d) {
		return (((Math.abs(c1.getPosRelX() - c2.getPosRelX())) + (Math.abs(c1.getPosRelY() - c2.getPosRelY()))) <= d);
	}

	public int distA(Ninja n) {
		return ((Math.abs(this.getCuadro().getPosRelX() - n.getCuadro().getPosRelX()))
				+ (Math.abs(this.getCuadro().getPosRelY() - n.getCuadro().getPosRelY())));
	}

	private void meMovi() {
		this.banderaMov = true;

	}

	private void yaAtaque() {
		this.banderaAtt = true;

	}

	public boolean atacaA(Cuadro cuadro) {
		if (distancia(this.cuadro, cuadro, this.distAtt)) {
			if (!banderaAtt && !cuadro.ninjaIsNull() && this.cuadro != cuadro
					&& (cuadro.getNinja().idequipo != this.idequipo)) {
				cuadro.atacaronATuNinja(this.att);
				this.toggleMenu();
				this.yaAtaque();
				this.meMovi();

				return true;
			} else {
				this.toggleMenu();
				return false;
			}

		} else {
			this.toggleMenu();
			return false;
		}

	}

	public void teAtacaron(float danio) {
		this.restarVida(danio);
	}

	private void restarVida(float v) {
		if (v > vida) {
			vida = 0;
		} else
			vida -= v;
	}

	private void restarVidaPrueba() {
		Random r = new Random();
		if (r.nextInt(10) < 4) {
			restarVida(10);
		}
		;
	}

	public boolean chequearSiMurio() {
		if (vida <= 0) {
			return true;
		} else
			return false;
	}

	public void rest() {
		setBanderaAtt(true);
		setBanderaMov(true);
		toggleMenu();
	}

	public MenuNinja getMenu() {
		return menu;
	}

	public void setMenu(MenuNinja menu) {
		this.menu = menu;
	}

	public void resetBandMyA() {
		this.banderaAtt = false;
		this.banderaMov = false;
	}

	public Cuadro getCuadro() {
		return cuadro;
	}

	public void setCuadro(Cuadro cuadro) {
		this.cuadro = cuadro;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	private void setXEnCuadro(Cuadro cuadro, int t) {
		this.x = cuadro.getX() + ((cuadro.getWidth() - t) / 2);
	}

	private void setYEnCuadro(Cuadro cuadro, int t) {
		this.y = cuadro.getY() + ((cuadro.getHeight() - t) / 2);
	}

	public float getVida() {
		return vida;
	}

	public void setVida(float vida) {
		this.vida = vida;
	}

	public int getDistMov() {
		return distMov;
	}

	public void setDistMov(int distMov) {
		this.distMov = distMov;
	}

	public int getDistAtt() {
		return distAtt;
	}

	public void setDistAtt(int distAtt) {
		this.distAtt = distAtt;
	}

	public boolean getBanderaMov() {
		return banderaMov;
	}

	public void setBanderaMov(boolean banderaMov) {
		this.banderaMov = banderaMov;
	}

	public boolean getBanderaAtt() {
		return banderaAtt;
	}

	public void setBanderaAtt(boolean banderaAtt) {
		this.banderaAtt = banderaAtt;
	}

	public float getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(float vidaMax) {
		this.vidaMax = vidaMax;
	}

	public Color getNombreNormal() {
		return nombreNormal;
	}

	public void setNombreNormal(Color nombreNormal) {
		this.nombreNormal = nombreNormal;
	}

	public IDEquipo getIdequipo() {
		return idequipo;
	}

	public void setIdequipo(IDEquipo idequipo) {
		this.idequipo = idequipo;
	}

	public boolean menuIsNull() {
		return (this.menu == null);
	}

	public IDEquipo getIdEquipo() {
		return this.idequipo;
	}

	public boolean seClickeo(int mx, int my) {
		if (clickCuadroOMenu(mx, my) && puedeHacerAlgo()) {
			return true;
		} else
			return false;
	}

	private boolean clickCuadroOMenu(int mx, int my) {
		return (seClickeoCuadro(mx, my) || seClickeoMenu(mx, my));
	}

	public boolean seClickeoCuadro(int mx, int my) {
		return getCuadro().seClickeo(mx, my);
	}

	public boolean seClickeoMenu(int mx, int my) {
		if (!menuIsNull()) {
			return getMenu().seClickeo(mx, my);
		} else
			return false;
	}

	public void ocultarMenu() {
		if (!menuIsNull()) {
			menu.ocultarMenu();
		}
	}

	public void decidiQueHacer(int mx, int my) {
		menu.decidiQueHacer(mx, my, this);
	}

	public void pediQueTeColoreenLosCercanos(int nro, Color c) {

		getTablero().colorearCercanos(getCuadro(), nro, c);
	}

	private Tablero getTablero() {
		return getCuadro().getT();
	}

	public void resetMenu() {
		if (!menuIsNull()) {
			toggleMenu();
		}
	}

}
