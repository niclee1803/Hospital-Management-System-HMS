package Records;
public enum BloodType {
    A_NEGATIVE,
    A_POSITIVE,
    B_NEGATIVE,
    B_POSITIVE,
    O_NEGATIVE,
    O_POSITIVE;

    @Override
    public String toString() {
        switch(this) {
            case A_NEGATIVE:
                return "A-";
            case A_POSITIVE:
                return "A+";
            case B_NEGATIVE:
                return "B-";
            case B_POSITIVE:
                return "B+";
            case O_NEGATIVE:
                return "O-";
            case O_POSITIVE:
                return "O+";
            default:
                return "";
        }
    }
}