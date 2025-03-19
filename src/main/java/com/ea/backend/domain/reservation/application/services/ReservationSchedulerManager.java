package com.ea.backend.domain.reservation.application.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ReservationSchedulerManager {

    @Autowired
    private ReservationService reservationService;


    @Scheduled(cron = "0 0 * * * *")
    public void runUpdateReservationsStatusBatch() {
        this.reservationService.updateExpiredReservations();
    }


}
