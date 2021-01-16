package persons;

public enum MaskValue {
    masked(0.2),
    unmasked(1.0);
    MaskValue(double val){
        this.val = val;
    }
    private final double val;
    public double getVal() {
        return val;
    }
}
