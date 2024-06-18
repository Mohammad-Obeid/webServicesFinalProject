package com.example.webservicesfinalproject.Repository;

import com.example.webservicesfinalproject.Entity.HouseKeepingTasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<HouseKeepingTasks,Integer> {
    Optional<HouseKeepingTasks> findByRoomRoomID(int roomID);
}
