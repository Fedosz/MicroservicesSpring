package ru.authservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.authservice.models.db.Person;

@Repository
public interface PersonRep extends JpaRepository<Person, Integer> {
}
