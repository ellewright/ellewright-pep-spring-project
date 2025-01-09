package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addNewMessage(Message newMessage) {
        if (!newMessage.getMessageText().equals("") && newMessage.getMessageText().length() < 256) {
            return messageRepository.save(newMessage);
        }

        return null;
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public Message findMessage(int messageId) {
        List<Message> messageList = (List<Message>) messageRepository.findAll();
        for (Message message : messageList) {
            if (message.getMessageId().equals(messageId)) {
                return message;
            }
        }

        return null;
    }

    public int deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }

        return 0;
    }

    public void updateMessage(Message updatedMessage) throws ResourceNotFoundException {
        List<Message> messageList = (List<Message>) messageRepository.findAll();
        if (messageList.removeIf(message -> message.getMessageId().equals(updatedMessage.getMessageId()))) {
            messageList.add(updatedMessage);
            return;
        }
    }
}
