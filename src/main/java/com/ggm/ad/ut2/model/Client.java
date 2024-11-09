package com.ggm.ad.ut2.model;
import java.time.LocalDateTime;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String email;
    private int purchases;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Client(int id, String name, String surname, String email, int purchases) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.purchases = purchases;
    }

    public Client(String name, String surname, String email, int purchases) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.purchases = purchases;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public Client() {

    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getPurchases() { return purchases; }
    public void setPurchases(int purchases) { this.purchases = purchases; }

    public LocalDateTime getCreateDate() { return createDate; }
    public LocalDateTime getUpdateDate() { return updateDate; }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", purchases=" + purchases + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return id == client.id;
    }
}

