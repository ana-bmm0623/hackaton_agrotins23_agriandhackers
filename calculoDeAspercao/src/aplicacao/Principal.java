package aplicacao;

import modelo.Cultura;

public class Principal {
	public static void main(String[] args) {
		Cultura c = new Cultura(0.35, 0.18, 450.0, 0.5, 7.0, 0.8, 10.02, 20.0, 323, 3.66, 18.0, 13.0);
//		c.lerDados();
		c.imprimir();
		
	}
}
