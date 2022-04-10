package com.edgardo.ionixtest.dto;

import java.util.List;

public class UserResponse {
    private List<UserDTO> users;
    private int page;
    private int itemsPerPages;
    private Long countElements;
    private int countPages;
    private boolean last;

    public UserResponse() {

    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPages() {
        return itemsPerPages;
    }

    public void setItemsPerPages(int itemsPerPages) {
        this.itemsPerPages = itemsPerPages;
    }

    public Long getCountElements() {
        return countElements;
    }

    public void setCountElements(Long countElements) {
        this.countElements = countElements;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
