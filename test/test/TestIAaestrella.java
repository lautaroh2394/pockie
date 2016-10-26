package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import enums.IDEquipo;
import ia.IAaestrella;
import ninjas.EquipoAI;
import ninjas.EquipoNinja;
import ninjas.Ninja;
import ninjas.Obstaculo;
import repositorioNinjas.GeneradorNinjas;
import tablero.Cuadro;
import tablero.Tablero;

public class TestIAaestrella {
	
	Tablero tab;
	Ninja cpu;
	EquipoAI e;
	
	@Before
	public void setUp() {
		tab = new Tablero(5,9,1200,900);
		
		GeneradorNinjas gen = new GeneradorNinjas(tab);
		e = gen.generarEquipoColoresNombreHyIAI(1, IDEquipo.CPU, Color.red, Color.blue);
		
		LinkedList<EquipoNinja> equipos = new LinkedList<EquipoNinja>();
		equipos.add(e);
		tab.setEquipos(equipos);
		
		cpu = tab.getEquipos().get(0).ninjas.get(0);
		cpu.movete(tab.getCuadros().get(25));
		cpu.resetBandMyA();
		e.setIA(new IAaestrella());
	}
	
	public void obstaculos(){
		tab.agregarObstaculo(tab.getCuadros().get(14));
		tab.agregarObstaculo(tab.getCuadros().get(23));
		tab.agregarObstaculo(tab.getCuadros().get(32));
	}
	
	@Test
	public void testAdy(){
		obstaculos();
		HashSet<Cuadro> cuadros = e.ia.adyacentes(cpu.getCuadro(), tab);
		assertEquals(4,cuadros.size());
		
	}
	
	@Test
	public void testAdyConObs(){
		obstaculos();
		tab.agregarObstaculo(tab.getCuadros().get(26));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		HashSet<Cuadro> cuadros = e.ia.adyacentes(cpu.getCuadro(), tab);
		assertEquals(2,cuadros.size());
	}
	
	@Test
	public void testAdySinObj(){
		obstaculos();
		tab.obstaculos=new LinkedList<Obstaculo>();
		assertEquals(0, tab.obstaculos.size());
		HashSet<Cuadro> cuadros = e.ia.adyacentes(cpu.getCuadro(), tab);
		assertEquals(4,cpu.getDistMov());
		
		assertEquals(4,cuadros.size());
	}
	
	
	
	@Test
	public void testAdyConObs2(){
		obstaculos();
		tab.agregarObstaculo(tab.getCuadros().get(5));
		tab.agregarObstaculo(tab.getCuadros().get(6));
		tab.agregarObstaculo(tab.getCuadros().get(7));
		tab.agregarObstaculo(tab.getCuadros().get(8));
		tab.agregarObstaculo(tab.getCuadros().get(41));
		tab.agregarObstaculo(tab.getCuadros().get(42));
		tab.agregarObstaculo(tab.getCuadros().get(43));
		tab.agregarObstaculo(tab.getCuadros().get(44));
		
		LinkedList<Cuadro> cuadros = e.ia.posicionesALasQuePuedeIr(cpu, tab);
		assertEquals(9,cuadros.size());
	}
	
	@Test
	public void testAdyConObs3(){
		tab.agregarObstaculo(tab.getCuadros().get(14));
		tab.agregarObstaculo(tab.getCuadros().get(23));
		tab.agregarObstaculo(tab.getCuadros().get(5));
		tab.agregarObstaculo(tab.getCuadros().get(16));
		tab.agregarObstaculo(tab.getCuadros().get(17));
		tab.agregarObstaculo(tab.getCuadros().get(42));
		tab.agregarObstaculo(tab.getCuadros().get(43));
		tab.agregarObstaculo(tab.getCuadros().get(44));
		
		LinkedList<Cuadro> cuadros = e.ia.posicionesALasQuePuedeIr(cpu, tab);
		assertEquals(11,cuadros.size());
	}
	
	

}
