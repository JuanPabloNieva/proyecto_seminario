
package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.ObraSocial;

public interface ObraSocialDao {

    void create(ObraSocial obraSocial);

    void update(ObraSocial obraSocial);

    void remove(Integer codigo);

    ObraSocial find(Integer codigo);

    List<ObraSocial> findAll();

}
