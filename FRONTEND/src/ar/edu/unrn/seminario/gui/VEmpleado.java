package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.MemoryApi;

public class VEmpleado extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panelPrincipal;
    private MemoryApi api = new MemoryApi();

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaEmpleado ventanaEmpleado = new VentanaEmpleado();
                    ventanaEmpleado.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

   
    public VEmpleado() {
        setTitle("SISTEMA SALUD - RECEPCIÓN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBounds(0, 0, 434, 22);
        panelPrincipal.add(barraMenu);

        
        JMenu menuPacientes = new JMenu("Pacientes");
        barraMenu.add(menuPacientes);

        JMenuItem opcionRegistrarPaciente =
                new JMenuItem("Registrar Paciente");

        opcionRegistrarPaciente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaPaciente ventanaAltaPaciente = new AltaPaciente(api);
                ventanaAltaPaciente.setDefaultCloseOperation(
                        JFrame.DISPOSE_ON_CLOSE);
                ventanaAltaPaciente.setVisible(true);
            }
        });

        menuPacientes.add(opcionRegistrarPaciente);

        JMenuItem opcionListadoPacientes =
                new JMenuItem("Listado de Pacientes");

        opcionListadoPacientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoPaciente ventanaListadoPacientes =
                        new ListadoPaciente(api);
                ventanaListadoPacientes.setVisible(true);
            }
        });

        menuPacientes.add(opcionListadoPacientes);

     
        JMenu menuTurnos = new JMenu("Turnos");
        barraMenu.add(menuTurnos);

        JMenuItem opcionRegistrarTurno =
                new JMenuItem("Registrar Turnos");

        opcionRegistrarTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaTurno ventanaAltaTurno = new AltaTurno(api);
                ventanaAltaTurno.setVisible(true);
            }
        });

        menuTurnos.add(opcionRegistrarTurno);

        JMenuItem opcionListadoTurnos =
                new JMenuItem("Listado de Turnos");

        opcionListadoTurnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoTurnos ventanaListadoTurnos =
                        new ListadoTurnos(api);
                ventanaListadoTurnos.setVisible(true);
            }
        });

        menuTurnos.add(opcionListadoTurnos);

        
        JMenu menuConfiguracion = new JMenu("Configuración");
        barraMenu.add(menuConfiguracion);

        JMenuItem opcionCerrarSesion =
                new JMenuItem("Cerrar Sesión");
        opcionCerrarSesion.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });

        menuConfiguracion.add(opcionCerrarSesion);
    }
}	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 22);
		contentPane.add(menuBar);
		
		JMenu mnRegistrarUsuario = new JMenu("Usuarios");
		menuBar.add(mnRegistrarUsuario);
		
		JMenuItem mntmMedico = new JMenuItem("Registrar Medico");
		mnRegistrarUsuario.add(mntmMedico);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Registrar Empleado");
		mnRegistrarUsuario.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Listado de Usuarios");
		mnRegistrarUsuario.add(mntmNewMenuItem);
		
		JMenu mnNewMenu = new JMenu("Pacientes");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Registrar Paciente");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Listado de Pacientes");
		mnNewMenu.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_1 = new JMenu("Turnos");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Registrar Turnos");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Listado de Turnos");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_2 = new JMenu("Configuracion");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3_1 = new JMenuItem("Cerrar Sesión");
		mnNewMenu_2.add(mntmNewMenuItem_3_1);

	}
}