package entities;

public class MedRequests {

    private String Medicine;
    private int amount; 
    private String unit;
    private String status;
    

    public MedRequests(String Medicine, int amount, String unit, String status)
    {
        this.Medicine = Medicine;
        this.amount = amount;
        this.unit = unit;
        this.status = status;
        
        
    }

    public String getMedicine(){
        return Medicine;

    }

    public int getAmount(){
        return amount;
    }
    public String getUnit(){
        return unit;
    }
    public String getStatus()
    {
        return status;
    }

    public String[] toArray() {
        return new String[]{Medicine, String.valueOf(amount), unit, status};
    }
}
