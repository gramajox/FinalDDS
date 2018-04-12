package com.example.xgramajo.tabbedproject;


import android.media.ImageReader;
import android.os.Parcel;
import android.os.Parcelable;

public class ProductClass /**esto funcionaba*//* implements Serializable*/ /**esto es para pasar el objeto de la lista a otra activity*/ implements Parcelable {

    private String name;
    private String description;
    private String category;
    private int price;
    private int mImageResource;

    public ProductClass(String name, String description, String category, int price, int mImageResource) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.mImageResource = mImageResource;
    }

    protected ProductClass(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
        price = in.readInt();
        mImageResource = in.readInt();
    }

    public static final Creator<ProductClass> CREATOR = new Creator<ProductClass>() {
        @Override
        public ProductClass createFromParcel(Parcel in) {
            return new ProductClass(in);
        }

        @Override
        public ProductClass[] newArray(int size) {
            return new ProductClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductClass getProduct() {
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(category);
        parcel.writeInt(price);
        parcel.writeInt(mImageResource);
    }
}
