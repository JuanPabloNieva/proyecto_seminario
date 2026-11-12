
package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Medico;

public interface MedicoDao {

    void create(Medico medico);

    void update(Medico medico);

    void remove(String matricula);

    Medico find(String matricula);

    List<Medico> findAll();

}
