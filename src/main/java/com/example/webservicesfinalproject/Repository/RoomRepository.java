package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Optional <List<Room>> findAllByStatus(String status);
}
