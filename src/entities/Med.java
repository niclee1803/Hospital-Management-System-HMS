package entities;

public class Med {
    private String name;
    private int currStock;
    private String unit; 

    public Med(String name, int currStock, String unit){
        this.name = name;
        this.currStock = currStock;
        this.unit = unit; 
    }

    public String getName(){
        return name;
    }
    public int getStock(){
        return currStock;
    }
    public String getUnit(){
        return unit;
    }


    
}
