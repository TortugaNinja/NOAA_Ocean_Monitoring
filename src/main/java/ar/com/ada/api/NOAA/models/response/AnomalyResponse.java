package ar.com.ada.api.NOAA.models.response;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.ada.api.NOAA.entities.Device.DeviceStatusEnum;

public class AnomalyResponse {

    public AnomalyResponse() {

    }

    public AnomalyResponse(BigDecimal alturaNivelDelMar, Date horarioInicioAnomalia, Date horarioFinAnomalia,
            DeviceStatusEnum tipoAlerta) {

        this.alturaNivelDelMar = alturaNivelDelMar;
        this.horarioInicioAnomalia = horarioInicioAnomalia;
        this.horarioFinAnomalia = horarioFinAnomalia;
        this.tipoAlerta = tipoAlerta;
    }

    BigDecimal alturaNivelDelMar;

    Date horarioInicioAnomalia;

    Date horarioFinAnomalia;

    DeviceStatusEnum tipoAlerta = DeviceStatusEnum.ALERTA_KAIJU;

}
