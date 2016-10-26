package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import enums.IDEquipo;
import ninjas.EquipoAI;
import ninjas.EquipoNinja;
import ninjas.Ninja;
import ninjas.Obstaculo;
import repositorioNinjas.GeneradorNinjas;
import tablero.Cuadro;
import tablero.Tablero;

public class TestIA {
	
	Tablero tab;
	Ninja cpu;
	EquipoAI e;

	@Before
	public void setUp() {
		tab = new Tablero(5,9,1200,900);
		tab.agregarObstaculo(tab.getCuadros().get(14));
		tab.agregarObstaculo(tab.getCuadros().get(23));
		tab.agregarObstaculo(tab.getCuadros().get(32));
		
		GeneradorNinjas gen = new GeneradorNinjas(tab);
		e = gen.generarEquipoColoresNombreHyIAI(1, IDEquipo.CPU, Color.red, Color.blue);
		
		LinkedList<EquipoNinja> equipos = new LinkedList<EquipoNinja>();
		equipos.add(e);
		tab.setEquipos(equipos);
		
		cpu = tab.getEquipos().get(0).ninjas.get(0);
		cpu.movete(tab.getCuadros().get(25));
		cpu.resetBandMyA();	
	}
	
	@Test
	public void test1(){
		
		assertTrue(cpu.pasos == null);
	}
	@Test
	public void testAdyacentes(){
		
		LinkedList<Cuadro> tem = e.ia.adyacentes(cpu, tab);
		assertTrue(tem!=null);
		String n = "";
		for (Cuadro c: tem){
			n=n+"("+c.getPosRelX()+","+c.getPosRelY()+")";
		}
		
		assertEquals(n,"(8,2)(6,2)(7,3)(7,1)");
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		assertEquals(e.ia.adyacenteYVacio(cpu,tab,1, pasos), true);
		assertEquals(e.ia.adyacenteYVacio(cpu,tab,-1, pasos), true);
		assertEquals(e.ia.adyacenteXVacio(cpu,tab,1, pasos), true);
		assertEquals(e.ia.adyacenteXVacio(cpu,tab,-1, pasos), true);
		
		tab.agregarObstaculo(tab.getCuadros().get(34));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		assertEquals(e.ia.adyacenteYVacio(cpu,tab,-1, pasos), false);
		assertEquals(e.ia.adyacenteYVacio(cpu,tab,1, pasos), false);
		
		tab.agregarObstaculo(tab.getCuadros().get(24));
		tab.agregarObstaculo(tab.getCuadros().get(26));
		assertEquals(e.ia.adyacenteXVacio(cpu,tab,-1, pasos), false);
		assertEquals(e.ia.adyacenteXVacio(cpu,tab,1, pasos), false);
	}
	
	@Test
	public void testEvadir(){
		tab.agregarObstaculo(tab.getCuadros().get(24));
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeEvadir(cpu,tab.getCuadros().get(21),tab,pasos);
		assertTrue(b);
	}
	
	@Test
	public void testEvadir2(){
		tab.agregarObstaculo(tab.getCuadros().get(24));
		Obstaculo o = (Obstaculo) tab.getCuadros().get(24).getNinja();
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeEvadirPorX(cpu,tab.getCuadros().get(21),tab,pasos);
		assertTrue(b);
	}
	
	@Test
	public void testEvadir3(){
		tab.agregarObstaculo(tab.getCuadros().get(34));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeEvadirPorY(cpu,tab.getCuadros().get(21),tab,pasos);
		assertFalse(b);
	}
	
	@Test
	public void testEvadir4(){
		tab.agregarObstaculo(tab.getCuadros().get(24));
		tab.agregarObstaculo(tab.getCuadros().get(26));
		tab.agregarObstaculo(tab.getCuadros().get(34));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeEvadir(cpu,tab.getCuadros().get(21),tab,pasos);
		assertFalse(b);
	}
	
	public void testEvadir5(){
		tab.agregarObstaculo(tab.getCuadros().get(24));
		tab.agregarObstaculo(tab.getCuadros().get(34));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeEvadir(cpu,tab.getCuadros().get(21),tab,pasos);
		assertTrue(b);
		b = e.ia.puedeEvadirPorY(cpu,tab.getCuadros().get(21),tab,pasos);
		assertFalse(b);
		b = e.ia.puedeEvadirPorX(cpu,tab.getCuadros().get(21),tab,pasos);
		assertTrue(b);
	}
	
	public void testEvadir6(){
		tab.agregarObstaculo(tab.getCuadros().get(24));
		tab.agregarObstaculo(tab.getCuadros().get(34));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro()); pasos.add(tab.getCuadros().get(26));
		boolean b = e.ia.puedeEvadir(cpu,tab.getCuadros().get(21),tab,pasos);
		assertFalse(b);
	}
	
	@Test
	public void testAcercarse(){
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeAcercarse(cpu, tab.getCuadros().get(21),tab, pasos);
		assertTrue(b);
		
	}
	
	@Test
	public void testAcercarse2(){
		tab.agregarObstaculo(tab.getCuadros().get(24));
		LinkedList<Cuadro> pasos = new LinkedList<Cuadro>(); pasos.add(cpu.getCuadro());
		boolean b = e.ia.puedeAcercarse(cpu, tab.getCuadros().get(21),tab, pasos);
		assertFalse(b);		
	}
	

}
