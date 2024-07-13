package it.esercizio.dao;

import org.springframework.data.repository.CrudRepository;

import it.esercizio.model.Coordinata;

public interface CoordinataDao extends CrudRepository<Coordinata, Integer> {

}
