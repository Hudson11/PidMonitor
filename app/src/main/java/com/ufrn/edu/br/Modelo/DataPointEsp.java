package com.ufrn.edu.br.Modelo;

public class DataPointEsp {

    Double setPoint;
    Double espPoint;
    Integer time;

    public DataPointEsp(Double setPoint, Double espPoint, Integer time) {
        this.setPoint = setPoint;
        this.espPoint = espPoint;
        this.time = time;
    }

    public  DataPointEsp(){}

    public Double getSetPoint() {
        return setPoint;
    }

    public void setSetPoint(Double setPoint) {
        this.setPoint = setPoint;
    }

    public Double getEspPoint() {
        return espPoint;
    }

    public void setEspPoint(Double espPoint) {
        this.espPoint = espPoint;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
