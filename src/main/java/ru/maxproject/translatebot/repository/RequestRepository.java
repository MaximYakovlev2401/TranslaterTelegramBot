package ru.maxproject.translatebot.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.maxproject.translatebot.model.Client;
import ru.maxproject.translatebot.model.Request;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends CrudRepository <Request, UUID>{

    List<Request> getRequestsByChatId(Pageable pageable, Long chatId);
}
