package motorhomes.com.examproject.applicationLogic;

import java.time.LocalDate;

public enum SeasonMultiplier {
    LOW(1, LocalDate.of(2018,1,1), LocalDate.of(2018,12,31)),
    MEDIUM(1.3, LocalDate.of(2018,5,1), LocalDate.of(2018,10,31)),
    PEAK(2.08, LocalDate.of(2018,7,1), LocalDate.of(2018,8,31));

    private final double multiplier;
    private final LocalDate startDate;
    private final LocalDate endDate;

    SeasonMultiplier(double multiplier, LocalDate startDate, LocalDate endDate){
        this.multiplier = multiplier;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getMultiplier(){
        return this.multiplier;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
