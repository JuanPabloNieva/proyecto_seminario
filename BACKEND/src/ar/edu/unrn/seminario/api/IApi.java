package ar.edu.unrn.seminario.api;

import java.util.List;
import java.util.Set;

import ar.edu.unrn.seminario.dto.EspecialidadDTO;
import ar.edu.unrn.seminario.dto.MedicoDTO;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.dto.PacienteDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TurnoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.Medico;
import ar.edu.unrn.seminario.modelo.Paciente;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface IApi {

	void registrarUsuario(String username, String password, String email, Integer rol) throws DataEmptyException ;
	
	void registrarMedico(String nombre, String apellido, int dni, String matricula, int Especialidad, Usuario usuario) throws DataEmptyException ;
	
	void registrarPaciente(String nombre, String apellido, int dni, Integer obraSocial) throws DataEmptyException ;
	
	void registrarTurno(MedicoDTO medico, TurnoDTO turno, PacienteDTO pacienteTurno) throws DataEmptyException;
	
	void modificarPaciente(String nombre, String apellido, int dni, Integer obraSocial) throws DataEmptyException ;

	UsuarioDTO obtenerUsuario(String username);

	void eliminarUsuario(String username);
	
	void eliminarPaciente(String dniPaciente);

	List<RolDTO> obtenerRoles();
	
	List<TurnoDTO> obtenerTurnosAsignados();
	
	List<ObraSocialDTO> obtenerObrasSociales();
	
	List<MedicoDTO> obtenerMedicos();

	List<RolDTO> obtenerRolesActivos();
	
	List<TurnoDTO> obtenerHistoriaClinica(Paciente paciente);
	
	Set<EspecialidadDTO> obtenerEspecialidades();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	PacienteDTO obtenerPacientePorDni(Integer dniPaciente);
	
	EspecialidadDTO obtenerEspecialidad(Integer codigo);
	
	MedicoDTO obtenerMedico(String matricula);
	
	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios
	
	List<PacienteDTO> obtenerPacientes();

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	boolean autenticar(String username, String password);
}
