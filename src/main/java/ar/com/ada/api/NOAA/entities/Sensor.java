package ar.com.ada.api.NOAA.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.com.ada.api.NOAA.entities.Device.DeviceStatusEnum;

@Entity
@Table(name = "sensor")

public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private Integer sensorId;

    @OneToOne
    @JoinColumn(name = "boya_id", referencedColumnName = "boya_id")
    private Boya boya;

    @OneToMany(mappedBy = "sensor")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Barco> embarcaciones = new ArrayList<>();

    @Column(name = "device_status_id")
    private Integer deviceStatus;

    @Column(name = "sea_level")
    private BigDecimal alturaNivelDelMar;

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public Boya getBoya() {
        return boya;
    }

    public void setBoya(Boya boya) {
        this.boya = boya;
    }

    public List<Barco> getEmbarcaciones() {
        return embarcaciones;
    }

    public void setEmbarcaciones(List<Barco> embarcaciones) {
        this.embarcaciones = embarcaciones;
    }

    public DeviceStatusEnum getDeviceStatus() {
        return DeviceStatusEnum.parse(this.deviceStatus);
    }

    public void setDeviceStatus(DeviceStatusEnum deviceStatus) {
        this.deviceStatus = deviceStatus.getValue();
    }

    public BigDecimal getAlturaNivelDelMar() {
        return alturaNivelDelMar;
    }

    public void setAlturaNivelDelMar(BigDecimal alturaNivelDelMar) {
        this.alturaNivelDelMar = alturaNivelDelMar;
    }

   

    
    

    
}