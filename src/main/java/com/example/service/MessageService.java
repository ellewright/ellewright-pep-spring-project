package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addNewMessage(Message newMessage) {
        List<Message> messageList = (List<Message>) messageRepository.findAll();

        for (Message message : messageList) {
            if (newMessage.getPostedBy().equals(message.getPostedBy())) {
                if (!newMessage.getMessageText().equals("") && newMessage.getMessageText().length() < 256) {
                    return messageRepository.save(newMessage);
                }
            }
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

    public Message updateMessage(Message updatedMessage) {
        if (!updatedMessage.getMessageText().equals("") && updatedMessage.getMessageText().length() < 256) {
            return messageRepository.save(updatedMessage);
        }

        return null;
    }

    public List<Message> getMessagesByPostedBy(int postedBy) {
        List<Message> messages = messageRepository.findByPostedBy(postedBy);
        return messages;
    }
}
