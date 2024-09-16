package thi.nguyen.whats_app.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String chat_name;
    private String chat_image;

    @ManyToOne
    @JoinColumn(name = "req_user_id")
    private User reqUser;

    @ManyToMany
    private Set<User> admins = new HashSet<>();

    @Column(name = "is_group")
    private boolean isGroup;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany
    private List<Message> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(Integer id, String chat_name, String chat_image, User reqUser, Set<User> admins, boolean isGroup, User createdBy, Set<User> users, List<Message> messages) {
        this.id = id;
        this.chat_name = chat_name;
        this.chat_image = chat_image;
        this.reqUser = reqUser;
        this.admins = admins;
        this.isGroup = isGroup;
        this.createdBy = createdBy;
        this.users = users;
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public User getReqUser() {
        return reqUser;
    }

    public void setReqUser(User reqUser) {
        this.reqUser = reqUser;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return isGroup == chat.isGroup && Objects.equals(id, chat.id) && Objects.equals(chat_name, chat.chat_name) && Objects.equals(chat_image, chat.chat_image) && Objects.equals(reqUser, chat.reqUser) && Objects.equals(admins, chat.admins) && Objects.equals(createdBy, chat.createdBy) && Objects.equals(users, chat.users) && Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chat_name, chat_image, reqUser, admins, isGroup, createdBy, users, messages);
    }
}
