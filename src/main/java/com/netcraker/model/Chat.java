package com.netcraker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
    private int friendId;
    private int userCurrentId;
    private int chatId;
    private String chatName;
    private int[] usersId;
    private int groupChatId;
    private int groupChatUsersId;
}

