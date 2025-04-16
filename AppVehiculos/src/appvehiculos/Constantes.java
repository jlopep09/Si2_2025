package appvehiculos;

public class Constantes {
    public enum ColumnasContribuyentes {
        SHEET(0),
        NIFNIE(0),
        APELLIDO1(1),
        APELLIDO2(2),
        NOMBRE(3),
        DIRECCION(4),
        NUMERO(5),
        EMAIL(6),
        AYUNTAMIENTO(7),
        PAISCCC(8),
        CCC(9),
        IBAN(10),
        BONIFICACION(11);

        private final int valor;

        ColumnasContribuyentes(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }
    }
    public enum ColumnasVehiculos {
        SHEET(1),
        TIPO(0),
        MARCA(1),
        MODELO(2),
        MATRICULA(3),
        BASTIDOR(4),
        CABALLOS(5),
        PLAZAS(6),
        KG(7),
        CC(8),
        EXENCION(9),
        FECHAMATRICULACION(10),
        FECHAALTA(11),
        FECHABAJA(12),
        FECHABAJATEMPORAL(13),
        NIFPROPIETARIO(14);

        private final int valor;

        ColumnasVehiculos(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }
    }
    public enum ColumnasOrdenanzas {
        CORRECTO(0),
        SUBSANADO(1),
        ERRONEO(2),
        IRREPARABLE(3);

        private final int valor;

        ColumnasOrdenanzas(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }
    }
}
