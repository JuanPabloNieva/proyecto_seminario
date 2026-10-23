package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.BorderLayout;

public class PanelRecepcionistaDefault extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelRecepcionistaDefault() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitulo = new JLabel("Sistema Salud");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo);

	}

}
