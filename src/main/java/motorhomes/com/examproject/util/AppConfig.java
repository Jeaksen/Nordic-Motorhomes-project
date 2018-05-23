package motorhomes.com.examproject.util;

import motorhomes.com.examproject.applicationLogic.HomepageManager;
import motorhomes.com.examproject.applicationLogic.RepairsManager;
import motorhomes.com.examproject.repositories.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class AppConfig {

//    @Bean
//    public DBConnector getConnector() throws SQLException {
//        return new DBConnector();
//    }

    @Bean
    public UsersDBRepository getUserDBRepository() {
        return new UsersDBRepository();
    }

    @Bean
    public ReservationsAccessoriesRepository getReservationsAccessoriesRepository() {
        return new ReservationsAccessoriesRepository();
    }

    @Bean
    public ReservationsRepository getReservationsRepository() throws SQLException {
        return new ReservationsRepository();
    }

    @Bean
    public RepairsDbRepository getRepairsDbRepository() {
        return new RepairsDbRepository();
    }

    @Bean
    public PickupDbRepository getPickupDbRepository() {
        return new PickupDbRepository();
    }

    @Bean
    public MotorhomeDescriptionDbRepository getMotorhomeDescriptionDbRepository() {
        return new MotorhomeDescriptionDbRepository();
    }

    @Bean
    public MotorhomeDbRepository getMotorhomeDbRepository() {
        return new MotorhomeDbRepository(this.getMotorhomeDescriptionDbRepository());
    }

    @Bean
    public DropOffDbRepository getDropOffDbRepository() {
        return new DropOffDbRepository();
    }

    @Bean
    public CustomersDbRepository getCustomersDbRepository() {
        return new CustomersDbRepository();
    }

    @Bean
    public AccessoriesDbRepository getAccessoriesDbRepository() {
        return new AccessoriesDbRepository();
    }

    @Bean
    public HomepageManager getHomepageManager() {
        return new HomepageManager(this.getUserDBRepository());
    }

    @Bean
    public RepairsManager getRepairsManager() {
        return new RepairsManager(this.getRepairsDbRepository());
    }
}
