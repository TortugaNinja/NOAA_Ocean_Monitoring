package ar.com.ada.api.NOAA.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.NOAA.entities.Muestra;

@Repository
public interface MuestraRepository extends JpaRepository<Muestra, Integer> {

    @Query("select CASE WHEN count(m) > 0 THEN true ELSE false END from Muestra m where m.muestraId =:muestraId")
    boolean muestraAlreadyExists(Integer muestraId);

    List<Muestra> findMuestrasByMuestraId(Integer muestraId);

    List<Muestra> findTodasLasMuestrasByMuestraId();

    Muestra findByMuestraId(Integer muestraId);

    Muestra findByAlturaNivelDelMar(BigDecimal alturaNivelDelMar);

    List<Muestra> findByListAlturaNivelDelMar(BigDecimal alturaNivelDelMar);
    
}