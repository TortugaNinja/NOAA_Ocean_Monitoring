package ar.com.ada.api.NOAA.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.NOAA.entities.Boya;
import ar.com.ada.api.NOAA.entities.Muestra;
import ar.com.ada.api.NOAA.entities.Device.DeviceStatusEnum;
import ar.com.ada.api.NOAA.entities.Device.FaroStatusEnum;
import ar.com.ada.api.NOAA.entities.Device.TipoDeviceEnum;
import ar.com.ada.api.NOAA.models.response.AnomalyResponse;
import ar.com.ada.api.NOAA.repositories.BoyaRepository;

@Service
public class BoyaService {

    @Autowired
    BoyaRepository boyaRepo;

    @Autowired
    BoyaService boyaService;

    @Autowired
    MuestraService muestraService;

    @Autowired
    SensorService sensorService;

    public boolean boyaYaExiste(Boya boya) {

        if (boyaRepo.boyaAlreadyExists(boya.getBoyaId()))

            return false;
        

        return true;
    }

    public Boya crearBoya(BigDecimal longitudInstalacion, BigDecimal latitudInstalacion) {

        Boya boya = new Boya();
        TipoDeviceEnum tipoDeviceId = TipoDeviceEnum.BOYA;
        boya.setTipoDeviceId(tipoDeviceId);
        boya.setLongitudInstalacion(longitudInstalacion);
        boya.setLatitudInstalacion(latitudInstalacion);
        // agregar validacion de la existencia del sensor
        DeviceStatusEnum deviceStatusPorDefecto = DeviceStatusEnum.A_NIVEL;
        BigDecimal alturaNivelDelMarPorDefecto = BigDecimal.valueOf(0);
        sensorService.crearSensor(deviceStatusPorDefecto, alturaNivelDelMarPorDefecto);

        boolean boyaExistente = boyaYaExiste(boya);

        if (!boyaExistente) {
            
            boyaRepo.save(boya);
            return boya;

        } else {

            return null;
        }

    }

    public List<Boya> obtenerBoyas() {

        List<Boya> boyas = boyaRepo.findAllBoyas();

        return boyas;
    }

    public Boya obtenerPorBoyaId(Integer boyaId) {

        Boya boyaEncontrada = new Boya();

        List<Boya> boyas = boyaRepo.findByBoyaId(boyaId);

        for (Boya boya : boyas) {

            if (boya.getBoyaId().equals(boyaId))

                boyaEncontrada = boya;
                return boyaEncontrada;

        }

        return boyaEncontrada;

    }

    public Boya updateSoloColorFaro(Integer boyaId, FaroStatusEnum faroStatus) {

        Boya boya = new Boya();

        boya = boyaService.obtenerPorBoyaId(boyaId);

        switch (faroStatus) {

            case AZUL:

                boya.setFaroStatusId(faroStatus);
                boyaRepo.save(boya);
                break;

            case VERDE:

                boya.setFaroStatusId(faroStatus);
                boyaRepo.save(boya);
                break;

            case AMARILLO:

                boya.setFaroStatusId(faroStatus);
                boyaRepo.save(boya);
                break;

            case ROJO:

                boya.setFaroStatusId(faroStatus);
                boyaRepo.save(boya);
                break;

            default:
                break;
        }

        return boya;

    }

    public Boya updateBoyaSensorStatus(Integer boyaId, BigDecimal alturaNivelDelMar) {

        Boya boya = new Boya();
        // decidir si creo metodo en MuestraService y en MuestraRepository para crear la
        // muestra en BoyaService. O si uso este metodo en MuestraService para crear la
        // muestra.

        boya = boyaService.obtenerPorBoyaId(boyaId);
        Boya boyaUpdate = null;

        if (alturaNivelDelMar.compareTo(BigDecimal.valueOf(0)) == 0
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == 1
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == -1) {

            boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
            boya.getSensor().setDeviceStatus(DeviceStatusEnum.A_NIVEL);
            boya.setFaroStatusId(FaroStatusEnum.VERDE);
            boyaRepo.save(boya);
            boyaUpdate = boya;
            

        } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == 0
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == -1)
                && alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == 1)) {

            boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
            boya.getSensor().setDeviceStatus(DeviceStatusEnum.HUNDIDA);
            boya.setFaroStatusId(FaroStatusEnum.AMARILLO);
            boyaRepo.save(boya);
            boyaUpdate = boya;
            

        } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == 0
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == 1)
                && alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == -1)) {

            boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
            boya.getSensor().setDeviceStatus(DeviceStatusEnum.SOBRE_NIVEL);
            boya.setFaroStatusId(FaroStatusEnum.AMARILLO);
            boyaRepo.save(boya);
            boyaUpdate = boya;
            

        } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == 0
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == -1)
                && alturaNivelDelMar.compareTo(BigDecimal.valueOf(-200)) == 1)) {

            boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
            boya.getSensor().setDeviceStatus(DeviceStatusEnum.HUNDIDA);
            boya.setFaroStatusId(FaroStatusEnum.ROJO);
            boyaRepo.save(boya);
            boyaUpdate = boya;
            

        } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == 0
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == 1)
                && alturaNivelDelMar.compareTo(BigDecimal.valueOf(200)) == -1)) {

            boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
            boya.getSensor().setDeviceStatus(DeviceStatusEnum.SOBRE_NIVEL);
            boya.setFaroStatusId(FaroStatusEnum.ROJO);
            boyaRepo.save(boya);
            boyaUpdate = boya;
            

        }

        return boyaUpdate;

    }

    public Boya updateBoyaSensorAbnormalStatus(Integer boyaId, BigDecimal alturaNivelDelMar) {

        Boya boya = new Boya();
        boya = boyaService.obtenerPorBoyaId(boyaId);
        Boya boyaAnomalyUpdate = null;

        if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-200)) == 0
                || alturaNivelDelMar.compareTo(BigDecimal.valueOf(200)) == 0))) {

            boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
            boya.getSensor().setDeviceStatus(DeviceStatusEnum.ALERTA_KAIJU);
            boya.setFaroStatusId(FaroStatusEnum.ROJO);
            boyaRepo.save(boya);
            boyaAnomalyUpdate = boya;
        
        }

        return boyaAnomalyUpdate;

    }

    public Boya updateBoyaSensorAbnormalStatusIMPACTO(Integer boyaId, BigDecimal alturaNivelDelMar) {

        Boya boya = new Boya();
        boya = boyaService.obtenerPorBoyaId(boyaId);
        Boya boyaAnomalyUpdate = null;

        boya.getSensor().setAlturaNivelDelMar(alturaNivelDelMar);
        boya.getSensor().setDeviceStatus(DeviceStatusEnum.ALERTA_IMPACTO);
        boya.setFaroStatusId(FaroStatusEnum.ROJO);
        boyaRepo.save(boya);
        boyaAnomalyUpdate = boya;
        
        return boyaAnomalyUpdate;

    }

    public AnomalyResponse boyaDetectarAnomaliaKAIJU(Integer boyaId) {

        Muestra muestra = new Muestra();
        Muestra muestraXBoyaId = new Muestra();
        Boya boya = new Boya();
        AnomalyResponse anomaly = null;
        List<Muestra> muestras = boya.getMuestras();
        List<Muestra> muestrasXBoyaId = new ArrayList<>();

        muestrasXBoyaId = muestras.stream().filter(m -> muestra.getBoya().getBoyaId() == boyaId)
                .collect(Collectors.toList());

        for (int i = 0; i < muestrasXBoyaId.size(); i++) {

            long horarioAnomalia = 0;

            long minutos10EnMilisegundos = 600000;

            long horarioMuestra = muestraXBoyaId.getHorarioMuestra().getTime();

            horarioAnomalia = horarioMuestra + minutos10EnMilisegundos;


            for (int j = 0; j < muestrasXBoyaId.size(); j++) {

                long horarioM = muestraXBoyaId.getHorarioMuestra().getTime();

                if ((horarioAnomalia == horarioM) && (muestraXBoyaId.getBoya().getBoyaId() == boyaId) && (i < j)) {

                    Date horarioInicioAnomalia = new Date(horarioM);

                    Date horarioFinAnomalia = new Date(horarioAnomalia);

                    Integer mBoyaId = muestraXBoyaId.getBoya().getBoyaId();

                    BigDecimal alturaNivelDelMar = muestraXBoyaId.getBoya().getSensor().getAlturaNivelDelMar();

                    this.updateBoyaSensorAbnormalStatus(mBoyaId, alturaNivelDelMar);

                    DeviceStatusEnum tipoAlerta = muestraXBoyaId.getBoya().getSensor().getDeviceStatus();

                    AnomalyResponse a = new AnomalyResponse(alturaNivelDelMar, horarioInicioAnomalia,
                            horarioFinAnomalia, tipoAlerta);

                    anomaly = a;
                    
                    
                }

            }

        }

        return anomaly;

    }

    public AnomalyResponse boyaDetectarAnomaliaIMPACTO(Integer boyaId) {

        Muestra muestra = new Muestra();
        Muestra muestraXBoyaId = new Muestra();
        Boya boya = new Boya();
        AnomalyResponse anomaly = null;
        List<Muestra> muestras = boya.getMuestras();
        List<Muestra> muestrasXBoyaId = new ArrayList<>();

        muestrasXBoyaId = muestras.stream().filter(m -> muestra.getBoya().getBoyaId() == boyaId)
                .collect(Collectors.toList());

        for (int i = 0; i < muestrasXBoyaId.size(); i++) {

            Date horarioM1 = muestraXBoyaId.getHorarioMuestra();

            BigDecimal seaLevelM1 = muestraXBoyaId.getAlturaNivelDelMar();

            for (int j = 1; j < muestrasXBoyaId.size(); j++) {

                Date horarioM2 = muestraXBoyaId.getHorarioMuestra();

                BigDecimal seaLevelM2 = muestraXBoyaId.getAlturaNivelDelMar();

                if (((seaLevelM1.compareTo(BigDecimal.valueOf(-500)) == 0 || seaLevelM1.compareTo(BigDecimal.valueOf(500)) == 0) && (seaLevelM2.compareTo(BigDecimal.valueOf(-500)) == 0 || seaLevelM2.compareTo(BigDecimal.valueOf(500)) == 0))) {

                    BigDecimal alturaNivelDelMar = seaLevelM2;

                    this.updateBoyaSensorAbnormalStatusIMPACTO(boyaId, alturaNivelDelMar);

                    Date horarioInicioAnomalia = horarioM1;

                    Date horarioFinAnomalia = horarioM2;

                    DeviceStatusEnum tipoAlerta = muestraXBoyaId.getBoya().getSensor().getDeviceStatus();

                    AnomalyResponse a = new AnomalyResponse(alturaNivelDelMar, horarioInicioAnomalia, horarioFinAnomalia, tipoAlerta);

                    anomaly = a;

                }
                    
                
                
            }
            
           
            
        }

        return anomaly;


    }

}
