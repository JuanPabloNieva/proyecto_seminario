package ar.edu.unrn.seminario.main;

import java.awt.EventQueue;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.gui.VentanaPrincipal;
import ar.edu.unrn.seminario.gui.VentanaRecepcionista;

public class Main {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					IApi api = new MemoryApi();
					//VentanaPrincipal frame = new VentanaPrincipal(api);
					VentanaRecepcionista frame = new VentanaRecepcionista(api);

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
