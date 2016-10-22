package repositorioNinjas;

import java.util.Random;

public class Nombre {
	
	private String[] nombres = {"Deidara","Naruto","Kakashi","Sasuke","Sakura",
								"Ten Ten","RockLee","Gai","Neji","Tsunade","Nagato",
								"Pain","Zabuza","Kisame","Jiraiya","Shikamaru","Minato",
								"Madara", "Tobi", "Obito","Rin", "Sarutobi", "Asuma"};
	public String devolverNombre(){
		Random r = new Random();
		return nombres[r.nextInt(nombres.length)];
	}

}
