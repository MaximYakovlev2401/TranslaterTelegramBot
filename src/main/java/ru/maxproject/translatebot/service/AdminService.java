package ru.maxproject.translatebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.maxproject.translatebot.model.Client;
import ru.maxproject.translatebot.model.Request;
import ru.maxproject.translatebot.repository.ClientRepository;
import ru.maxproject.translatebot.repository.RequestRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final RequestRepository requestRepository;
    private final ClientRepository clientRepository;

    public List<String> getMessagesById(Long chatId) {
        return requestRepository.getRequestsByChatId(chatId)
                .stream()
                .map(Request::getMessage)
                .toList();
    }

    public List<String> getAllClientsIds(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .stream()
                .map(Client::getId)
                .map(Object::toString)
                .toList();
    }
}
