public class HeadCoach {
    private String name;
    private double dunkPreference;
    private double layupPreference;
    private double midRangePreference;
    private double threePointPreference;
    private double postUpPreference;
    private double closeShotPreference;
    private double midRangeShotPreference;
    private double threePointShotPreference;
    private double postFadePreference;
    private double passPreference;
    private double passSuccessThreshold;
    private double stealSuccessThreshold;

    public HeadCoach(String name, double dunkPreference, double layupPreference, double midRangePreference, double threePointPreference, double postUpPreference) {
        this.name = name;
        this.dunkPreference = dunkPreference;
        this.layupPreference = layupPreference;
        this.midRangePreference = midRangePreference;
        this.threePointPreference = threePointPreference;
        this.postUpPreference = postUpPreference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDunkPreference() {
        return dunkPreference;
    }

    public void setDunkPreference(double dunkPreference) {
        this.dunkPreference = dunkPreference;
    }

    public double getLayupPreference() {
        return layupPreference;
    }

    public void setLayupPreference(double layupPreference) {
        this.layupPreference = layupPreference;
    }

    public double getMidRangePreference() {
        return midRangePreference;
    }

    public void setMidRangePreference(double midRangePreference) {
        this.midRangePreference = midRangePreference;
    }

    public double getThreePointPreference() {
        return threePointPreference;
    }

    public void setThreePointPreference(double threePointPreference) {
        this.threePointPreference = threePointPreference;
    }

    public double getPostUpPreference() {
        return postUpPreference;
    }

    public void setPostUpPreference(double postUpPreference) {
        this.postUpPreference = postUpPreference;
    }

    public double getCloseShotPreference() {
        return closeShotPreference;
    }

    public double getMidRangeShotPreference() {
        return midRangeShotPreference;
    }

    public double getThreePointShotPreference() {
        return threePointShotPreference;
    }

    public double getPostFadePreference() {
        return postFadePreference;
    }

    public double getPassPreference() {
        return passPreference;
    }

    public double getPassSuccessThreshold() {
        return passSuccessThreshold;
    }

    public void setPassSuccessThreshold(double passSuccessThreshold) {
        this.passSuccessThreshold = passSuccessThreshold;
    }

    public double getStealSuccessThreshold() {
        return stealSuccessThreshold;
    }

    public void setStealSuccessThreshold(double stealSuccessThreshold) {
        this.stealSuccessThreshold = stealSuccessThreshold;
    }
}
