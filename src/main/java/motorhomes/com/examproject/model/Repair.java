package motorhomes.com.examproject.model;

public class Repair {
    private int repairId;
    private String problem;
    private int motorhomeId;
    private String repairStatus;

    public Repair(){

    }

    public Repair(int repairId, String problem, int motorhomeId, String repairStatus){
        this.repairId = repairId;
        this.problem = problem;
        this.motorhomeId = motorhomeId;
        this.repairStatus = repairStatus;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getMotorhomeId() {
        return motorhomeId;
    }

    public void setMotorhomeId(int motorhomeId) {
        this.motorhomeId = motorhomeId;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairId=" + repairId +
                ", problem='" + problem + '\'' +
                ", motorhomeId=" + motorhomeId +
                ", repairStatus='" + repairStatus + '\'' +
                '}';
    }
}
