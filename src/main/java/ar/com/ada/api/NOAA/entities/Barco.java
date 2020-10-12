package ar.com.ada.api.NOAA.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.ada.api.NOAA.entities.Device.TipoDeviceEnum;

@Entity
@Table(name = "barco")
public class Barco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barco_id")
    private Integer barcoId;

    @Column(name = "matricula_embarcacion")
    private String matricula;

    @Column(name = "tipo_device_id")
    private Integer tipoDeviceId;

    private Integer radioStatus;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "sensor_id")
    private Sensor sensor;

    public enum RadioStatusEnum {
        ON(1), OFF(2);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private RadioStatusEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static RadioStatusEnum parse(Integer id) {
            RadioStatusEnum status = null; // Default
            for (RadioStatusEnum item : RadioStatusEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }


    public Integer getBarcoId() {
        return barcoId;
    }

    public void setBarcoId(Integer barcoId) {
        this.barcoId = barcoId;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public TipoDeviceEnum getTipoDeviceId() {
        return TipoDeviceEnum.parse(this.tipoDeviceId);
    }

    public void setTipoDeviceId(TipoDeviceEnum tipoDeviceId) {
        this.tipoDeviceId = tipoDeviceId.getValue();
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
        this.sensor.getEmbarcaciones().add(this);
    }

    public RadioStatusEnum getRadioStatus() {
        return RadioStatusEnum.parse(radioStatus);
    }

    public void setRadioStatus(RadioStatusEnum radioStatus) {
        this.radioStatus = radioStatus.getValue();
    }
}