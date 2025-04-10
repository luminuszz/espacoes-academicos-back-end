package com.ea.backend.infra.scripts;

import com.ea.backend.domain.reservation.enterprise.entity.ReservationStatus;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class InsertSeedData implements CommandLineRunner {

  @Value("${spring.datasource.url}")
  private String JDBC_URL;

  @Value("${spring.datasource.username}")
  private String USER;

  @Value("${spring.datasource.password}")
  private String PASSWORD;

  @Value("${application.config.database.run_seed}")
  private String RUN_SEED;

  @Override
  public void run(String... args) throws Exception {

    System.out.println("Insert SeedData seed  " + RUN_SEED);
    System.out.println("Insert SeedData seed  " + JDBC_URL);
    System.out.println("Insert SeedData seed  " + USER);
    System.out.println("Insert SeedData seed  " + PASSWORD);

    if (!Boolean.parseBoolean(RUN_SEED)) {
      return;
    }

    Faker faker = new Faker();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

      conn.setAutoCommit(false);

      // Insert 50 users
      String insertUserSQL =
          "INSERT INTO public.users (id, email, name, password_hash, role) VALUES (?, ?, ?, ?, ?)";

      // Create first admin user
      try {

        var stmt = conn.prepareStatement(insertUserSQL);

        UUID userId = UUID.randomUUID();
        stmt.setObject(1, userId);
        stmt.setObject(1, userId);
        stmt.setString(2, "admin@admin.com");
        stmt.setString(3, faker.name().fullName());
        stmt.setString(4, encoder.encode("admin")); // Fake password
        stmt.setString(5, "ADMIN");

        stmt.execute();

      } catch (RuntimeException e) {
        throw new RuntimeException(e);
      }

      List<UUID> userIds = new ArrayList<>();
      try (PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {
        for (int i = 1; i <= 50; i++) {
          UUID userId = UUID.randomUUID();
          userIds.add(userId);
          stmt.setObject(1, userId);
          stmt.setString(2, faker.internet().emailAddress());
          stmt.setString(3, faker.name().fullName());
          stmt.setString(4, encoder.encode(faker.internet().password())); // Fake password
          stmt.setString(
              5, i % 2 == 0 ? "ADMIN" : "TEACHER"); // Alternating between ADMIN and TEACHER
          stmt.addBatch();
        }
        stmt.executeBatch();
      }

      // Insert 50 spaces with random status
      String insertSpaceSQL =
          "INSERT INTO public.spaces (id, capacity, description, room_name, status, acronym) VALUES (?, ?, ?, ?, ?, ?)";
      List<UUID> spaceIds = new ArrayList<>();
      try (PreparedStatement stmt = conn.prepareStatement(insertSpaceSQL)) {
        for (int i = 1; i <= 50; i++) {
          UUID spaceId = UUID.randomUUID();
          spaceIds.add(spaceId);
          stmt.setObject(1, spaceId);
          stmt.setInt(
              2, faker.number().numberBetween(20, 120)); // Random capacity between 20 and 120
          stmt.setString(3, faker.lorem().sentence());
          stmt.setString(
              4, "Room " + faker.letterify("??") + i); // Room name based on letters and number
          stmt.setString(5, faker.bool().bool() ? "AVAILABLE" : "UNAVAILABLE"); // Random status
          stmt.setString(6, "ACRONYM-" + i); // Acronym
          stmt.addBatch();
        }
        stmt.executeBatch();
      }

      // Insert 50 reservations with random status and relationships
      String insertReservationSQL =
          "INSERT INTO public.reservations (id, end_date_time, start_date_time, academic_space_id, user_id, status) VALUES (?, ?, ?, ?, ?, ?)";
      Random random = new Random();
      try (PreparedStatement stmt = conn.prepareStatement(insertReservationSQL)) {
        for (int i = 1; i <= 50; i++) {
          UUID reservationId = UUID.randomUUID();
          UUID spaceId = spaceIds.get(random.nextInt(spaceIds.size()));
          UUID userId = userIds.get(random.nextInt(userIds.size()));
          Timestamp startDateTime =
              new Timestamp(
                  System.currentTimeMillis()
                      + random.nextInt(
                          7 * 24 * 60 * 60 * 1000)); // Random start time within the next 7 days
          Timestamp endDateTime =
              new Timestamp(
                  startDateTime.getTime()
                      + (random.nextInt(4) + 1)
                          * 60
                          * 60
                          * 1000); // Random end time 1 to 4 hours after start time
          String[] statuses = {
            ReservationStatus.CONFIRMED_BY_THE_ENTERPRISE.toString(),
            ReservationStatus.CONFIRMED_BY_THE_USER.toString(),
            ReservationStatus.PENDING.toString(),
            ReservationStatus.CANCELED.toString()
          };
          String status = statuses[random.nextInt(statuses.length)];

          stmt.setObject(1, reservationId);
          stmt.setTimestamp(2, endDateTime);
          stmt.setTimestamp(3, startDateTime);
          stmt.setObject(4, spaceId);
          stmt.setObject(5, userId);
          stmt.setString(6, status);
          stmt.addBatch();
        }
        stmt.executeBatch();
      }

        // Insert 50 school units
        String insertSchoolUnitSQL =
                "INSERT INTO public.unidade_escola (id, name, type, state, city, address, numero_contato, data_criacao) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSchoolUnitSQL)) {
            for (int i = 1; i <= 50; i++) {
                UUID schoolUnitId = UUID.randomUUID();
                stmt.setObject(1, schoolUnitId);
                stmt.setString(2, faker.educator().university());
                stmt.setString(3, faker.options().option("Public", "Private"));
                stmt.setString(4, faker.address().state());
                stmt.setString(5, faker.address().city());
                stmt.setString(6, faker.address().streetAddress());

                var contactNumber = faker.phoneNumber().phoneNumber();
                if (contactNumber.length() > 15) {
                    contactNumber = contactNumber.substring(0, 15);
                }

                stmt.setString(7, contactNumber);
                stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis())); // Current timestamp
                stmt.addBatch();
            }
            stmt.executeBatch();
        }

      // Commit the transaction
      conn.commit();
      System.out.println("Data inserted successfully!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}