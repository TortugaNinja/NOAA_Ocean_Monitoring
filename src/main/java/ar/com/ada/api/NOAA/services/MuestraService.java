package ar.com.ada.api.NOAA.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.NOAA.entities.Barco;
import ar.com.ada.api.NOAA.entities.Boya;
import ar.com.ada.api.NOAA.entities.Muestra;
import ar.com.ada.api.NOAA.entities.Barco.RadioStatusEnum;
import ar.com.ada.api.NOAA.entities.Device.FaroStatusEnum;
import ar.com.ada.api.NOAA.entities.Device.TipoDeviceEnum;
import ar.com.ada.api.NOAA.models.response.MuestraSeaLevelMinResponse;
import ar.com.ada.api.NOAA.models.response.MuestraXColorResponse;
import ar.com.ada.api.NOAA.repositories.BarcoRepository;
import ar.com.ada.api.NOAA.repositories.BoyaRepository;
import ar.com.ada.api.NOAA.repositories.MuestraRepository;

@Service
public class MuestraService {

    @Autowired
    MuestraRepository muestraRepo;

    @Autowired
    BoyaRepository boyaRepo;

    @Autowired
    BarcoRepository barcoRepo;

    @Autowired
    BarcoService barcoService;

    @Autowired
    BoyaService boyaService;

    public boolean muestraYaExiste(Muestra muestra) {

        if (muestraRepo.muestraAlreadyExists(muestra.getMuestraId()))

            return false;

        return true;
    }

    public Muestra crearMuestra(Integer boyaId, Date horarioMuestra, String matricula, BigDecimal longitud,
            BigDecimal latitud, BigDecimal alturaNivelDelMar) {

        Muestra muestra = new Muestra();

        muestra.setHorarioMuestra(horarioMuestra);
        // agregar validacion de matricula de barco en null.
        if (this.matriculaIsNull(matricula) == true) {

            return null;

        } else {

            TipoDeviceEnum tipoDeviceId = TipoDeviceEnum.BARCO;
            RadioStatusEnum radioStatus = RadioStatusEnum.ON;
            barcoService.crearBarco(tipoDeviceId, matricula, radioStatus);
            List<Barco> barcos = muestra.getBoya().getSensor().getEmbarcaciones();

            for (Barco barco : barcos) {

                if (matricula.equals(barco.getMatricula())) {

                    muestra.setMatricula(matricula);
                }
            }

        }

        muestra.setLongitud(longitud);
        muestra.setLatitud(latitud);

        boyaService.updateBoyaSensorStatus(boyaId, alturaNivelDelMar);
        muestra.setAlturaNivelDelMar(muestra.getBoya().getSensor().getAlturaNivelDelMar());

        boolean muestraExistente = muestraYaExiste(muestra);

        if (!muestraExistente) {

            muestraRepo.save(muestra);
            return muestra;

        } else {

            return null;
        }

    }

    public List<Muestra> obtenerMuestras(Integer boyaId) {

        List<Muestra> muestras = boyaRepo.findMuestrasByBoyaId(boyaId);

        return muestras;
    }

    public boolean matriculaIsNull(String matricula) {

        if (matricula == null) {

            return true;

        } else {

            return false;
        }

    }

    public void deleteMuestra(Integer muestraId) {

        Boya boya = new Boya();

        for (Muestra muestra : boya.getMuestras()) {

            if (muestraId == muestra.getMuestraId()) {

                Integer boyaId = muestra.getBoya().getBoyaId();
                FaroStatusEnum faroStatus = FaroStatusEnum.AZUL;
                boyaService.updateSoloColorFaro(boyaId, faroStatus);
                muestraRepo.deleteById(muestraId);

            }
        }
    }

    public List<MuestraXColorResponse> buscarMuestrasXColor(FaroStatusEnum faroStatusEnum) {

        Boya boya = new Boya();

        List<MuestraXColorResponse> muestras = new ArrayList<>();

        for (Muestra muestra : boya.getMuestras()) {

            BigDecimal alturaNivelDelMar = muestra.getAlturaNivelDelMar();

            if (faroStatusEnum == FaroStatusEnum.VERDE) {

                if (alturaNivelDelMar.compareTo(BigDecimal.valueOf(0)) == 0
                        || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == 1
                        || alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == -1) {

                    Integer boyaId = muestra.getBoya().getBoyaId();
                    Date horario = muestra.getHorarioMuestra();
                    BigDecimal seaLevel = muestra.getAlturaNivelDelMar();
                    alturaNivelDelMar = seaLevel;

                    MuestraXColorResponse muestraVerde = new MuestraXColorResponse(boyaId, horario, alturaNivelDelMar);

                    MuestraXColorResponse.verdes.add(muestraVerde);

                }

                muestras = MuestraXColorResponse.verdes;

            } else if (faroStatusEnum == FaroStatusEnum.AMARILLO) {

                if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == 0
                        || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == -1)
                        && alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == 1)) {

                    Integer boyaId = muestra.getBoya().getBoyaId();
                    Date horario = muestra.getHorarioMuestra();
                    BigDecimal seaLevel = muestra.getAlturaNivelDelMar();
                    alturaNivelDelMar = seaLevel;

                    MuestraXColorResponse muestraAmarilla = new MuestraXColorResponse(boyaId, horario,
                            alturaNivelDelMar);

                    MuestraXColorResponse.amarillas.add(muestraAmarilla);

                } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == 0
                        || alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == 1)
                        && alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == -1)) {

                    Integer boyaId = muestra.getBoya().getBoyaId();
                    Date horario = muestra.getHorarioMuestra();
                    BigDecimal seaLevel = muestra.getAlturaNivelDelMar();
                    alturaNivelDelMar = seaLevel;

                    MuestraXColorResponse muestraAmarilla = new MuestraXColorResponse(boyaId, horario,
                            alturaNivelDelMar);

                    MuestraXColorResponse.amarillas.add(muestraAmarilla);

                }

                muestras = MuestraXColorResponse.amarillas;

            } else if (faroStatusEnum == FaroStatusEnum.ROJO) {

                if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == 0
                        || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == -1)
                        && alturaNivelDelMar.compareTo(BigDecimal.valueOf(-200)) == 1)) {

                    Integer boyaId = muestra.getBoya().getBoyaId();
                    Date horario = muestra.getHorarioMuestra();
                    BigDecimal seaLevel = muestra.getAlturaNivelDelMar();
                    alturaNivelDelMar = seaLevel;

                    MuestraXColorResponse muestraRoja = new MuestraXColorResponse(boyaId, horario, alturaNivelDelMar);

                    MuestraXColorResponse.rojas.add(muestraRoja);

                } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == 0
                        || alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == 1)
                        && alturaNivelDelMar.compareTo(BigDecimal.valueOf(200)) == -1)) {

                    Integer boyaId = muestra.getBoya().getBoyaId();
                    Date horario = muestra.getHorarioMuestra();
                    BigDecimal seaLevel = muestra.getAlturaNivelDelMar();
                    alturaNivelDelMar = seaLevel;

                    MuestraXColorResponse muestraRoja = new MuestraXColorResponse(boyaId, horario, alturaNivelDelMar);

                    MuestraXColorResponse.rojas.add(muestraRoja);

                }

                muestras = MuestraXColorResponse.rojas;

            }

        }

        return muestras;

    }

    public FaroStatusEnum buscarMuestraXColorYSeaLevel(BigDecimal alturaNivelDelMar, Integer muestraId) {
        
        Optional<Muestra> m = muestraRepo.findById(muestraId);

        FaroStatusEnum color = FaroStatusEnum.AZUL;

        if (m.isPresent() == true){

            if (alturaNivelDelMar.compareTo(BigDecimal.valueOf(0)) == 0
                    || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == 1
                    || alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == -1) {

                        color = FaroStatusEnum.VERDE;

            } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == 0
                    || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-50)) == -1)
                    && alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == 1)) {

                        color = FaroStatusEnum.AMARILLO;

            } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == 0
                    || alturaNivelDelMar.compareTo(BigDecimal.valueOf(50)) == 1)
                    && alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == -1)) {

                        color = FaroStatusEnum.AMARILLO;

            } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == 0
                    || alturaNivelDelMar.compareTo(BigDecimal.valueOf(-100)) == -1)
                    && alturaNivelDelMar.compareTo(BigDecimal.valueOf(-200)) == 1)) {

                        color = FaroStatusEnum.ROJO;

            } else if (((alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == 0
                    || alturaNivelDelMar.compareTo(BigDecimal.valueOf(100)) == 1)
                    && alturaNivelDelMar.compareTo(BigDecimal.valueOf(200)) == -1)) {
                        
                        color = FaroStatusEnum.ROJO;
            }

        } else {

            return null;
        }

        return color;

    }


    public BigDecimal obtenerAlturaNivelDelMarMinima(Integer boyaId) {

        Muestra muestra = new Muestra();
        Muestra muestraXBoyaId = new Muestra();
        Boya boya = new Boya();
        BigDecimal seaLevel;
        BigDecimal alturaNivelDelMar;
        BigDecimal seaLevelMin = null;
        List<Muestra> muestras = boya.getMuestras();
        List<Muestra> muestrasXBoyaId = new ArrayList<>();

        muestrasXBoyaId = muestras.stream().filter(m -> muestra.getBoya().getBoyaId() == boyaId)
                .collect(Collectors.toList());

        for (int i = 0; i < muestrasXBoyaId.size(); i++) {

            seaLevel = muestraXBoyaId.getAlturaNivelDelMar();

            for (Muestra m : muestrasXBoyaId) {

                alturaNivelDelMar = m.getAlturaNivelDelMar();

                if (alturaNivelDelMar.compareTo(seaLevel) == -1) {

                    seaLevelMin = alturaNivelDelMar;

                    return seaLevelMin;
                }

            }

        }

        return seaLevelMin;

    }

    public MuestraSeaLevelMinResponse obtenerMuestraConAlturaNilvelDelMarMinima(Integer boyaId) {

        Muestra muestraSeaLevelMin = new Muestra();

        BigDecimal seaLevelMin = this.obtenerAlturaNivelDelMarMinima(boyaId);

        muestraSeaLevelMin = muestraRepo.findByAlturaNivelDelMar(seaLevelMin);

        Integer muestraId = muestraSeaLevelMin.getMuestraId();

        FaroStatusEnum color = this.buscarMuestraXColorYSeaLevel(seaLevelMin, muestraId);

        BigDecimal alturaNivelDelMarMin = muestraSeaLevelMin.getAlturaNivelDelMar();

        Date horario = muestraSeaLevelMin.getHorarioMuestra();

        MuestraSeaLevelMinResponse muestraR = new MuestraSeaLevelMinResponse(color, alturaNivelDelMarMin, horario);

        return muestraR;
    }

}
