package ru.maxproject.translatebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.maxproject.translatebot.model.Client;
import ru.maxproject.translatebot.model.Request;
import ru.maxproject.translatebot.repository.ClientRepository;
import ru.maxproject.translatebot.repository.RequestRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final RequestRepository requestRepository;
    private final ClientRepository clientRepository;

    public List<String> getMessagesById(Pageable pageable, Long chatId) {
        return requestRepository.getRequestsByChatId(pageable,chatId)
                .stream()
                .map(Request::getMessage)
                .toList();
    }

    public List<String> getAllClientsIds() {
        return clientRepository.findAll()
                .stream()
                .map(Client::getId)
                .map(Object::toString)
                .toList();
    }
}
