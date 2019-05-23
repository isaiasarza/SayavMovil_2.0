package com.sayav.desarrollo.sayav20.mensaje;

public class TipoMensaje {

    protected String tipo;
    protected long quantum;
    protected long timetolive;

    public TipoMensaje(String tipo, long quantum, long timetolive) {
        this.tipo = tipo;
        this.quantum = quantum;
        this.timetolive = timetolive;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getQuantum() {
        return quantum;
    }

    public void setQuantum(long quantum) {
        this.quantum = quantum;
    }

    public long getTimetolive() {
        return timetolive;
    }

    public void setTimetolive(long timetolive) {
        this.timetolive = timetolive;
    }

    @Override
    public String toString() {
        return "TipoMensaje{" +
                "tipo='" + tipo + '\'' +
                ", quantum=" + quantum +
                ", timetolive=" + timetolive +
                '}';
    }
}
