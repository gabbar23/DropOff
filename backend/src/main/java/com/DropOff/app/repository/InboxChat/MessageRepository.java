package com.DropOff.app.repository.InboxChat;

import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.InboxChat.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * findBySenderAndReceiverOrderByCreatedAtDesc is a method to get messages by sender and receiver order by created at desc
     *
     * 
     * @param sender sender object
     * @param receiver receiver object
     * @return List<Message>
     */
    List<Message> findBySenderAndReceiverOrderByCreatedAtDesc(UserProfile sender, UserProfile receiver);

    /**
     * findByReceiverAndSenderOrderByCreatedAtDesc is a method to get messages by receiver and sender order by created at desc
     *
     * 
     * @param receiver receiver object
     * @param sender sender object
     * @return List<Message>
     */
    List<Message> findByReceiverAndSenderOrderByCreatedAtDesc(UserProfile receiver, UserProfile sender);

    /**
     * findDistinctSenderAndReceiverIdsByUserId is a method to get distinct sender and receiver ids by user id
     *
     * 
     * @param userId user id
     * @return List<Integer>
     */
    @Query(value = "SELECT DISTINCT m.receiver_id as id FROM messages as m WHERE m.sender_id = :userId UNION SELECT DISTINCT m.sender_id as id FROM messages as m WHERE m.receiver_id = :userId", nativeQuery = true)
    List<Integer> findDistinctSenderAndReceiverIdsByUserId(@Param("userId") Integer userId);

    /**
     * deleteMessagesBySenderAndReceiver is a method to delete messages by sender and receiver
     *
     * 
     * @param sender sender object
     * @param receiver receiver object
     */
    void deleteMessagesByReceiverAndSender(UserProfile receiver, UserProfile sender);

}
