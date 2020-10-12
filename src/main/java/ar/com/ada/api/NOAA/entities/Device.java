package ar.com.ada.api.NOAA.entities;

public class Device {

    public enum DeviceStatusEnum {

        HUNDIDA(-1), A_NIVEL(0), SOBRE_NIVEL(1), ALERTA_KAIJU(99), ALERTA_IMPACTO(999);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private DeviceStatusEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static DeviceStatusEnum parse(Integer id) {
            DeviceStatusEnum status = null;   // Default
            for (DeviceStatusEnum item : DeviceStatusEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            
            
            return status;
        }
    }

    public enum TipoDeviceEnum {
        BOYA(1), BARCO(2);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private TipoDeviceEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoDeviceEnum parse(Integer id) {
            TipoDeviceEnum status = null; // Default
            for (TipoDeviceEnum item : TipoDeviceEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public enum FaroStatusEnum {

        AZUL(0), VERDE(1), AMARILLO(2), ROJO(3);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private FaroStatusEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static FaroStatusEnum parse(Integer id) {
            FaroStatusEnum status = null;   // Default
            for (FaroStatusEnum item : FaroStatusEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            
            
            return status;
        }
    }



}