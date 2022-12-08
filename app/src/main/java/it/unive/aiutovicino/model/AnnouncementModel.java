package it.unive.aiutovicino.model;

import java.util.List;

public class AnnouncementModel {
    private String id;
    private String idUser;
    private String idCategory;
    private String title;
    private String description;
    private String place;
    private String date;
    private String hours;
    private int participantsNumber;
    private int coins;
    private boolean approved;

    private List<UserModel> userApplyed = null;

    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<UserModel> getUserApplyed() {
        return userApplyed;
    }

    public void setUserApplyed(List<UserModel> userApplyed) {
        this.userApplyed = userApplyed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
