package ar.com.ada.api.NOAA.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.NOAA.entities.Barco;
import ar.com.ada.api.NOAA.entities.Barco.RadioStatusEnum;
import ar.com.ada.api.NOAA.entities.Device.TipoDeviceEnum;
import ar.com.ada.api.NOAA.repositories.BarcoRepository;

@Service
public class BarcoService {

    @Autowired
    BarcoRepository barcoRepo;

    public boolean barcoYaExiste(Barco barco){


        if (barcoRepo.barcoAlreadyExists(barco.getBarcoId(), barco.getMatricula())) 

            return false;
            
        return true;
    }

    public Barco crearBarco(TipoDeviceEnum tipoDeviceId, String matricula, RadioStatusEnum radioStatus) {

    
        Barco barco = new Barco();
        barco.setTipoDeviceId(tipoDeviceId);
        barco.setMatricula(matricula);
        barco.setRadioStatus(radioStatus);
        boolean barcoExistente = barcoYaExiste(barco);

        if (!barcoExistente) {

            barcoRepo.save(barco);
            return barco;

        } else {

            return null;
        }
        
    }

    public List<Barco> obtenerBarcos() {

        List<Barco> barcos = barcoRepo.findTodosByMatricula();

        return barcos;
    }

    public Barco obtenerPorMatricula(String matricula) {

        Barco barcoEncontrado = new Barco();

        List<Barco> barcos = barcoRepo.findByMatricula(matricula);

        for (Barco barco : barcos) {

            if (barco.getMatricula().equals(matricula))

                barcoEncontrado = barco;
            
        }

        return barcoEncontrado;

    }

}
