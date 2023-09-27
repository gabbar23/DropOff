package com.DropOff.app.controller.InboxChat;

import com.DropOff.app.TestConstants;
import com.DropOff.app.controller.InboxChat.ChatController;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.InboxChat.InboxRequest;
import com.DropOff.app.model.InboxChat.Message;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.InboxChat.MessageRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatController chatController;


    @Test
    public void testSendMessage() {
        UserProfile sender = new UserProfile();
        sender.setId(TestConstants.USER_ONE_ID);

        UserProfile receiver = new UserProfile();
        receiver.setId(TestConstants.USER_TWO_ID);

        InboxRequest request = new InboxRequest();
        request.setSenderId(TestConstants.USER_ONE_ID);
        request.setReceiverId(TestConstants.USER_TWO_ID);
        request.setMessage("Hello");

        when(userRepository.findById(request.getSenderId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(request.getReceiverId())).thenReturn(Optional.of(receiver));

        ResponseEntity<?> responseEntity = chatController.sendMessage(request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void testSendMessageWhenNoUser() {
        InboxRequest request = new InboxRequest();
        request.setSenderId(TestConstants.USER_ONE_ID);
        request.setReceiverId(TestConstants.USER_TWO_ID);
        request.setMessage("Hello");

        ResponseEntity<?> responseEntity = chatController.sendMessage(request);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof String);
    }

    @Test
    public void testGetChatUsers() {
        UserProfile user = new UserProfile();
        user.setId(TestConstants.USER_ONE_ID);

        List<Integer> userIds = Arrays.asList(2, 3, 4);

        when(userRepository.findById(user.getUser_id())).thenReturn(Optional.of(user));
        when(messageRepository.findDistinctSenderAndReceiverIdsByUserId(user.getUser_id())).thenReturn(userIds);
        when(userRepository.getUserByIds(userIds)).thenReturn(Arrays.asList(new UserProfile(), new UserProfile(), new UserProfile()));

        List<UserProfile> chatUsers = chatController.getChatUsers(user.getUser_id());

        assertEquals(3, chatUsers.size());
        verify(messageRepository, times(1)).findDistinctSenderAndReceiverIdsByUserId(user.getUser_id());
        verify(userRepository, times(1)).getUserByIds(userIds);
    }

    @Test
    public void testGetChatUsersWhenNoUser() {
        UserProfile user = new UserProfile();
        user.setId(TestConstants.USER_ONE_ID);

        assertThrows(RuntimeException.class, () -> chatController.getChatUsers(user.getUser_id()));
    }

    @Test
    public void testRemoveMessage() {
        long messageId = 1L;

        chatController.removeMessage(messageId);

        verify(messageRepository, times(1)).deleteById(messageId);
    }


    @Test
    void testGetChatHistory() {
        // create userOne
        UserProfile userOne = new UserProfile();
        userOne.setId(1);
        userRepository.save(userOne);
        when(userRepository.findById(userOne.getUser_id())).thenReturn(Optional.of(userOne));

        // create userTwo
        UserProfile userTwo = new UserProfile();
        userTwo.setId(TestConstants.USER_TWO_ID);
        userRepository.save(userTwo);
        when(userRepository.findById(userTwo.getUser_id())).thenReturn(Optional.of(userTwo));

        // Prepare message from userOne to userTwo
        Message message1 = new Message();
        message1.setSender(userOne);
        message1.setReceiver(userTwo);
        message1.setMessage("Hello, userTwo!");
        message1.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message1);

        List<Message> messagesOneToTwo = new ArrayList<>();
        messagesOneToTwo.add(message1);

        when(messageRepository.findBySenderAndReceiverOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(messagesOneToTwo);

        // Prepare message from userTwo to userOne
        Message message2 = new Message();
        message2.setSender(userTwo);
        message2.setReceiver(userOne);
        message2.setMessage("Hi, userOne!");
        message2.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message2);

        List<Message> messagesTwoToOne = new ArrayList<>();
        messagesTwoToOne.add(message2);

        when(messageRepository.findByReceiverAndSenderOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(messagesTwoToOne);

        // get messages between userOne and userTwo
        List<Message> messages = chatController.getChatHistory(userOne.getUser_id(), userTwo.getUser_id());

        // check if messages are correct
        assertEquals(2, messages.size());
        assertEquals(message1.getMessage(), messages.get(0).getMessage());
        assertEquals(message2.getMessage(), messages.get(1).getMessage());
    }


    @Test
    public void testGetChatHistoryWhenNoUser() {

        // create userOne
        UserProfile userOne = new UserProfile();
        userOne.setId(TestConstants.USER_ONE_ID);

        // create userTwo
        UserProfile userTwo = new UserProfile();
        userTwo.setId(TestConstants.USER_TWO_ID);

        assertThrows(RuntimeException.class, () -> chatController.getChatHistory(userOne.getUser_id(), userTwo.getUser_id()));
    }

    @Test
    void testRemoveAllMessages() {
        // create userOne
        UserProfile userOne = new UserProfile();
        userOne.setId(TestConstants.USER_ONE_ID);
        userRepository.save(userOne);
        when(userRepository.findById(userOne.getUser_id())).thenReturn(Optional.of(userOne));

        // create userTwo
        UserProfile userTwo = new UserProfile();
        userTwo.setId(TestConstants.USER_TWO_ID);
        userRepository.save(userTwo);
        when(userRepository.findById(userTwo.getUser_id())).thenReturn(Optional.of(userTwo));

        // Prepare message from userOne to userTwo
        Message message1 = new Message();
        message1.setSender(userOne);
        message1.setReceiver(userTwo);
        message1.setMessage("Hello, userTwo!");
        message1.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message1);

        List<Message> messagesOneToTwo = new ArrayList<>();
        messagesOneToTwo.add(message1);

        when(messageRepository.findBySenderAndReceiverOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(messagesOneToTwo);

        // Prepare message from userTwo to userOne
        Message message2 = new Message();
        message2.setSender(userTwo);
        message2.setReceiver(userOne);
        message2.setMessage("Hi, userOne!");
        message2.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message2);

        List<Message> messagesTwoToOne = new ArrayList<>();
        messagesTwoToOne.add(message2);

        when(messageRepository.findByReceiverAndSenderOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(messagesTwoToOne);

        // get messages between userOne and userTwo
        List<Message> messages = chatController.getChatHistory(userOne.getUser_id(), userTwo.getUser_id());

        // check if messages are correct
        assertEquals(2, messages.size());
        assertEquals(message1.getMessage(), messages.get(0).getMessage());
        assertEquals(message2.getMessage(), messages.get(1).getMessage());


        // remove all messages
        chatController.removeAllMessages(userOne.getUser_id(), userTwo.getUser_id());

        when(messageRepository.findBySenderAndReceiverOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(new ArrayList<>());
        when(messageRepository.findByReceiverAndSenderOrderByCreatedAtDesc(userOne, userTwo)).thenReturn(new ArrayList<>());


        // check if messages are removed
        messages = chatController.getChatHistory(userOne.getUser_id(), userTwo.getUser_id());
        assertEquals(0, messages.size());
    }
    @Test
    void testRemoveAllMessagesWhenNoUsers() {
        // create userOne
        UserProfile userOne = new UserProfile();
        userOne.setId(TestConstants.USER_ONE_ID);

        // create userTwo
        UserProfile userTwo = new UserProfile();
        userTwo.setId(TestConstants.USER_TWO_ID);

        assertThrows(RuntimeException.class, () -> chatController.removeAllMessages(userOne.getUser_id(), userTwo.getUser_id()));
    }


}