package ru.maxproject.translatebot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.maxproject.translatebot.model.Client;
import ru.maxproject.translatebot.model.Request;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository <Client, UUID>{

    List<Client> findAll();
}
