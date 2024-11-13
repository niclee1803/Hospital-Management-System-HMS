package entities;

/**
 * Represents a medicine or medical supply item with its name, current stock, and unit of measurement
 */
public class Med {
    private String name;
    private int currStock;
    private String unit;

    /**
     * Constructs a Med object with the specified name, current stock, and unit of measurement
     * @param name The name of the medicine or item
     * @param currStock The current stock level of the medicine
     * @param unit The unit of measurement for the stock (eg tablets, ml, etc)
     */
    public Med(String name, int currStock, String unit){
        this.name = name;
        this.currStock = currStock;
        this.unit = unit; 
    }

    /**
     * Gets the name of the medicine or medicinal item
     * @return The name of the medicine
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the current stock level of the medicine
     * @return The current stock as an integer
     */
    public int getStock(){
        return currStock;
    }

    /**
     * Gets the unit of measurement for the medicine's stock
     * @return The unit of measurement
     */
    public String getUnit(){
        return unit;
    }


    
}
