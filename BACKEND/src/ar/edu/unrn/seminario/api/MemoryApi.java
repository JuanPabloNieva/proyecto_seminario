package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.Especialidad;
import ar.edu.unrn.seminario.modelo.Medico;
import ar.edu.unrn.seminario.modelo.ObraSocial;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class MemoryApi implements IApi {

	private Set<Rol> roles = new HashSet();
	private Set<ObraSocial> oSociales = new HashSet();
	private Set<Especialidad> especialidades = new HashSet();
	private List<Usuario> usuarios = new ArrayList<>();
	private List<Medico> medicos = new ArrayList<>();
	private Map<String, String> usuariosRegistrados = new HashMap<String, String>();

	public MemoryApi() {

		// datos iniciales
		this.roles.add(new Rol(1, "ADMIN"));
		this.roles.add(new Rol(2, "RECEPCIONISTA"));
		this.roles.add(new Rol(3, "MEDICO"));
		
		this.oSociales.add(new ObraSocial(1, "IPROSS"));
		this.oSociales.add(new ObraSocial(2, "OSDE"));
		this.oSociales.add(new ObraSocial(3, "SANCORSALUD"));

		this.especialidades.add(new Especialidad(1, "Pediatria"));
		this.especialidades.add(new Especialidad(2, "Cardiologia"));
		this.especialidades.add(new Especialidad(3, "Dermatologia"));

		inicializarUsuarios();
	}

	private void inicializarUsuarios() {

		try {
			registrarUsuario("admin", "1234", "admin@unrn.edu.ar", 1);
			registrarUsuario("ldifabio", "4", "ldifabio@unrn.edu.ar", 2);
			registrarUsuario("bjgorosito", "1234", "bjgorosito@unrn.edu.ar", 3);
			registrarUsuario("lgonzalez", "1222", "lgonzalez@unrn.edu.ar", 3);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void inicializarMedicos() {
		try {
			registrarMedico("Bruno", "Gorosito", 35353532, "AFA256", 1, usuarios.get(2));
			registrarMedico("Laura", "Gonzalez", 35893532, "AFG886", 2, usuarios.get(3));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void registrarUsuario(String username, String password, String email, Integer rol)
			throws DataEmptyException {

		Rol role = this.buscarRol(rol);
		Usuario usuario = new Usuario(username, password, email, role);
		usuariosRegistrados.put(usuario.getUsuario(), usuario.getContrasena());
		this.usuarios.add(usuario);

	}

	@Override
	public void registrarMedico(String nombre, String apellido, int dni, String matricula, int especialidad, Usuario usuario)
			throws DataEmptyException {
		Especialidad espec = this.buscarEspecialidad(especialidad);
		Medico medico = new Medico(nombre, apellido, dni, matricula, espec);
		medico.setUsuario(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		for (Usuario u : this.usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getEmail(), u.getRol().getNombre(),
					u.isActivo(), u.obtenerEstado()));
		}
		return dtos;
	}
	
	@Override
	public List<ObraSocialDTO> obtenerObrasSociales() {
		List<ObraSocialDTO> dtos = new ArrayList<>();
		for (ObraSocial os : this.oSociales) {
			dtos.add(new ObraSocialDTO(os.getCodigo(), os.getNombre(), os.isActivo()));
		}
		return dtos;
	}

	@Override
	public List<MedicoDTO> obtenerMedicos() {
		List<MedicoDTO> dtos = new ArrayList<>();
		for (Medico u : this.medicos) {
			dtos.add(new MedicoDTO(u.getNombre(), u.getApellido(), u.getDni(), u.getMatricula(), u.getEspecialidad(),
					u.getTurnosDisponibles()));
		}
		return dtos;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			if (r.isActivo())
				dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub
		Rol rol = new Rol(codigo, descripcion);
		this.roles.add(rol);
	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String usuario) {
		Usuario user = this.buscarUsuario(usuario);
		user.activar();
	}

	@Override
	public void desactivarUsuario(String usuario) {
		// TODO: desactivar usuario
	}

	private Rol buscarRol(Integer codigo) {
		for (Rol rol : roles) {
			if (rol.getCodigo().equals(codigo))
				return rol;
		}
		return null;
	}

	private Especialidad buscarEspecialidad(Integer codigo) {
		for (Especialidad espec : especialidades) {
			if (espec.getCodigo().equals(codigo))
				return espec;
		}
		return null;
	}

	private Usuario buscarUsuario(String usuario) {
		for (Usuario user : usuarios) {
			if (user.getUsuario().equals(usuario))
				return user;
		}
		return null;
	}

	public boolean autenticar(String username, String password) {
		if (username.equals("")) {
			return false;
		} else if (usuariosRegistrados.containsKey(username) && usuariosRegistrados.get(username).equals(password)) {
			return true;
		} else {
			return false;
		}

	}
}
