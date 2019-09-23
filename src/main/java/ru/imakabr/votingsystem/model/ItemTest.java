//package ru.imakabr.votingsystem.model;
//
//import org.hibernate.validator.constraints.Range;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.time.LocalDateTime;
//
//@Embeddable
//public class ItemTest {
//
//    @NotBlank
//    @Size(min = 2, max = 100)
//    @Column(name = "name", nullable = false)
//    protected String name;
//
//    @Column(name = "price", nullable = false)
//    @Range(min = 10, max = 500000)
//    private int price;
//
//    @Column(name = "date_time", nullable = false)
//    @NotNull
//    private LocalDateTime dateTime;
//
//    public ItemTest() {
//    }
//
//    public ItemTest(String name, int price, LocalDateTime dateTime) {
//        this.name = name;
//        this.price = price;
//        this.dateTime = dateTime;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public LocalDateTime getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(LocalDateTime dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    @Override
//    public String toString() {
//        return "Item{" +
//                "name = " + name +
//                ", date = " + dateTime +
//                ", price = " + price +
//                '}';
//    }
//}
