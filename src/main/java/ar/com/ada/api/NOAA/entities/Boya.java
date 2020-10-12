package ar.com.ada.api.NOAA.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.com.ada.api.NOAA.entities.Device.FaroStatusEnum;
import ar.com.ada.api.NOAA.entities.Device.TipoDeviceEnum;

@Entity
@Table(name = "boya")

public class Boya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boya_id")
    private Integer boyaId;

    @Column(name = "longitud_inicial")
    private BigDecimal longitudInstalacion;

    @Column(name = "latitud_inicial")
    private BigDecimal latitudInstalacion;

    @OneToMany(mappedBy = "boya", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Muestra> muestras = new ArrayList<>();

    @OneToOne(mappedBy = "boya", cascade = CascadeType.ALL)
    private Sensor sensor;

    @Column(name = "faro_status_id")
    private Integer faroStatusId;

    @Column(name = "tipo_device_id")
    private Integer tipoDeviceId;

	public Integer getBoyaId() {
		return boyaId;
	}

	public void setBoyaId(Integer boyaId) {
		this.boyaId = boyaId;
	}

	public BigDecimal getLongitudInstalacion() {
		return longitudInstalacion;
	}

	public void setLongitudInstalacion(BigDecimal longitudInstalacion) {
		this.longitudInstalacion = longitudInstalacion;
	}

	public BigDecimal getLatitudInstalacion() {
		return latitudInstalacion;
	}

	public void setLatitudInstalacion(BigDecimal latitudInstalacion) {
		this.latitudInstalacion = latitudInstalacion;
	}

	public List<Muestra> getMuestras() {
		return muestras;
	}

	public void setMuestras(List<Muestra> muestras) {
		this.muestras = muestras;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
		this.sensor.setBoya(this);
	}

	public FaroStatusEnum getFaroStatusId() {
		return FaroStatusEnum.parse(this.faroStatusId);
	}

	public void setFaroStatusId(FaroStatusEnum faroStatusId) {
		this.faroStatusId = faroStatusId.getValue();
	}

	public TipoDeviceEnum getTipoDeviceId() {
        return TipoDeviceEnum.parse(this.tipoDeviceId);
    }

    public void setTipoDeviceId(TipoDeviceEnum tipoDeviceId) {
        this.tipoDeviceId = tipoDeviceId.getValue();
    }


    
}