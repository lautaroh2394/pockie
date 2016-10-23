package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1550691097823471818L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH /12*9;
	
	private Thread thread;
	private boolean running;
	
	private Handler handler;
	
	public Game(){
		
		handler = new Handler(WIDTH, HEIGHT,this);
		//handler.agregarEquipos();
		handler.agregarUnNinjaAI();
		
		new Ventana(WIDTH,HEIGHT,"Pockie", this);
		
		
	}

	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running=false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		this.requestFocus();//hace que la ventana tenga el foco automaticamente, la trae "adelante" al iniciar
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns= 1000000000/amountOfTicks; //1 seg sobre amountOfTicks -> seria el intervalo entre ticks
		double delta=0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		
		while (running){
			long now = System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			while(delta>=1){
				tick();
				delta--;
			}
			if (running) {render();}
			frames++;
			
			if(System.currentTimeMillis()-timer>1000){
				timer+=1000;
				System.out.println("fps: "+frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		handler.tick();
		}
	
	private void render(){
		BufferStrategy bs= this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(3);//crea tres buffers en el game
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		

		//por ahora ponemos render() en orden de como queremos que grafique,
		//despues arma una clase que tiene en cuenta niveles de profundidad con lo que no importa 
		//en que orden le decimos que grafique
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max){
		if(var>= max)
			return var = max;
		else if (var<=min) return var = min;
		else return var;
	}
	
	public static void main(String args[]){
		new Game();
	}

}
