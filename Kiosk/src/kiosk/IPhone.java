package kiosk;

import javax.swing.*;

public class IPhone extends JPanel implements Device{
    public String name;
    public int index;
    public String[] storageOptions; // 용량 옵션
    public String[] colorOptions; // 색상 옵션
    public int[] priceOptions; // 가격 옵션
    public int code=1111;

    //IPhone iphonePanel;

    public IPhone(String name, int[] priceOptions, String[] colorOptions, String[] storageOptions,int index) {
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




