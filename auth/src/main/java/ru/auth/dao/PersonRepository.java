package ru.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.auth.models.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
