package entities;

/**
 * Represent a medical request, including the requested medicine, amount, unit of measurement and the status of request
 */
public class MedRequests {

    private String Medicine;
    private int amount; 
    private String unit;
    private String status;


    /**
     * Constructs a MedRequests object with the specified medicine name, amount, unit of measurement and status
     * @param Medicine The name of the requested medicine
     * @param amount The amount of the medicine being requested
     * @param unit The unit of measurement for the medicine (eg tablet, ml, etc)
     * @param status The status of the request (eg, pending, approved, rejected)
     */
    public MedRequests(String Medicine, int amount, String unit, String status)
    {
        this.Medicine = Medicine;
        this.amount = amount;
        this.unit = unit;
        this.status = status;
    }

    /**
     * Gets the name of the requested medicine
     * @return The name of the medicine
     */
    public String getMedicine(){
        return Medicine;

    }

    /**
     * Gets the amount of the requested medicine
     * @return The amount of medicne requested
     */
    public int getAmount(){
        return amount;
    }

    /**
     * Gets the unit of measurement for the requested medicine
     * @return The unit of measurement (eg tablets, ml, etc)
     */
    public String getUnit(){
        return unit;
    }

    /**
     * Gets the statys of the medical request
     * @return The status of the request (eg pending, approved, rejected)
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Converts the MedRequests objects into an array of Strings for easy display or processing
     * @return A string array containing the medicine name, amount, unit and status
     */
    public String[] toArray() {
        return new String[]{Medicine, String.valueOf(amount), unit, status};
    }
}
