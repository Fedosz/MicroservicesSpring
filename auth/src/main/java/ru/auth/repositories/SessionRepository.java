package ru.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.auth.models.Session;

public interface SessionRepository  extends JpaRepository<Session, Integer> {
}
