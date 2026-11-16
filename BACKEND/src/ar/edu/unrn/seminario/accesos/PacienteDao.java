package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Paciente;

public interface PacienteDao {

    void create(Paciente paciente);

    void update(Paciente paciente);

    void remove(Integer dni);

    Paciente find(Integer dni);

    List<Paciente> findAll();

}
