package kiosk;

import javax.swing.*;

public class Galaxy extends JPanel implements Device{
    public String name;
    public int index;
    public String[] storageOptions; // 용량 옵션
    public String[] colorOptions; // 색상 옵션
    public int[] priceOptions; // 가격 옵션
    // Galaxy galaxyPanel;
    public int code=3333;

    public Galaxy(String name, int[] priceOptions, String[] colorOptions, String[] storageOptions,int index) {
        this.name = name;
        this.storageOptions = storageOptions;
        this.colorOptions = colorOptions;
        this.priceOptions = priceOptions;
        this.index=index;

    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int[] getPriceOptions() {
        return priceOptions;
    }

    @Override
    public int getItem() {
        return code;
    }

}
