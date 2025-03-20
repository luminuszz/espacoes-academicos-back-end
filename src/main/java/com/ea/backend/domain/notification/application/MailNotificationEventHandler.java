package com.ea.backend.domain.notification.application;

import com.ea.backend.domain.notification.application.contracts.MailProvider;
import com.ea.backend.domain.reservation.enterprise.events.ReservationCanceledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MailNotificationEventHandler {

    @Autowired
    private MailProvider mailProvider;


    @EventListener
    public void onHandleReservationCanceledEvent(ReservationCanceledEvent event) {
        System.out.println("Mail Notification Event Handler onHandleReservationCanceledEvent");

        var reservation = event.getReservation();
        this.mailProvider.sendEmail(reservation.getUser().getEmail(), "Reserva Cancelada", "Sua reserva foi cancelada com sucesso");

    }

}
