package cn.datanalyse.pojo;

/**
 * 封装每一次的测试数据
 * @author XYQ
 */
public class Data {
    /**
     * 标号
     */
    private String biaoHao;
    /**
     * 坐标X
     */
    private Integer zuoBiaoX;
    /**
     * 坐标
     */
    private String zuoBiaoA;
    /**
     * 厚度L
     */
    private double houDuL;
    /**
     * 误差
     */
    private double wuCha;

    public Data(){}

    public Data(String biaoHao, Integer zuoBiaoX, String zuoBiaoA, double houDuL, double wuCha) {
        this.biaoHao = biaoHao;
        this.zuoBiaoX = zuoBiaoX;
        this.zuoBiaoA = zuoBiaoA;
        this.houDuL = houDuL;
        this.wuCha = wuCha;
    }

    public String getBiaoHao() {
        return biaoHao;
    }

    public void setBiaoHao(String biaoHao) {
        this.biaoHao = biaoHao;
    }

    public Integer getZuoBiaoX() {
        return zuoBiaoX;
    }

    public void setZuoBiaoX(Integer zuoBiaoX) {
        this.zuoBiaoX = zuoBiaoX;
    }

    public String getZuoBiaoA() {
        return zuoBiaoA;
    }

    public void setZuoBiaoA(String zuoBiaoA) {
        this.zuoBiaoA = zuoBiaoA;
    }

    public double getHouDuL() {
        return houDuL;
    }

    public void setHouDuL(double houDuL) {
        this.houDuL = houDuL;
    }

    public double getWuCha() {
        return wuCha;
    }

    public void setWuCha(double wuCha) {
        this.wuCha = wuCha;
    }
}
