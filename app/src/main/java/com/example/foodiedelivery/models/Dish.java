package com.example.foodiedelivery.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "dishes",
        foreignKeys = @ForeignKey(
                entity = Restaurant.class,
                parentColumns = "id",
                childColumns = "resId",
                onDelete = ForeignKey.CASCADE
        )
)

public class Dish {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;
//    private Integer id;

    @NonNull
    @ColumnInfo(name="resId")
    private Long resId;

    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="price")
    private Double price;

    public Dish(@NonNull Long resId, String name, Double price) {
        this.resId = resId;
        this.name = name;
        this.price = price;
    }
    @Ignore
    public Dish() {
    }
    @NonNull
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @NonNull
    public Long getResId() {
        return resId;
    }
    public void setResId(@NonNull Long resId) {
        this.resId = resId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public static DiffUtil.ItemCallback<Dish> itemCallback = new DiffUtil.ItemCallback<Dish>() {
        @Override
        public boolean areItemsTheSame(@NonNull Dish oldItem, @NonNull Dish newItem) {
            return oldItem.getId() == (newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Dish oldItem, @NonNull Dish newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return getId() == (dish.getId()) && getResId().equals(dish.getResId()) && getName().equals(dish.getName()) && getPrice().equals(dish.getPrice());
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", resId=" + resId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
