package com.task.taskshopping.item;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Productdb")
public class Productdb implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "discription")
    private String discription;

    @NonNull
    @ColumnInfo(name = "price")
    private String price;

    @NonNull
    @ColumnInfo(name = "count")
    private int count;
    public Productdb() {
    }

    public Productdb(@NonNull String name, @NonNull String discription, @NonNull String price, int count) {
        this.name = name;
        this.discription = discription;
        this.price = price;
        this.count = count;
    }

    protected Productdb(Parcel in) {
        id = in.readInt();
        name = in.readString();
        discription = in.readString();
        price = in.readString();
        count = in.readInt();
    }

    public static final Creator<Productdb> CREATOR = new Creator<Productdb>() {
        @Override
        public Productdb createFromParcel(Parcel in) {
            return new Productdb(in);
        }

        @Override
        public Productdb[] newArray(int size) {
            return new Productdb[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(@NonNull String discription) {
        this.discription = discription;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    public void setPrice(@NonNull String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(discription);
        dest.writeString(price);
        dest.writeInt(count);
    }
}
