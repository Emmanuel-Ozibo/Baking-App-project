package com.emma.bakingapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModels implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<IngeredientsResponse> ingredientsResponse;
    @SerializedName("steps")
    private List<StepsResponse> stepsResponses;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;


    public RecipeModels(String name){
        this.name = name;
    }

    public RecipeModels(String name, List<IngeredientsResponse> ingeredientsResponse){
        this.name = name;
        this.ingredientsResponse  = ingeredientsResponse;
    }

    public RecipeModels(String id, String name, List<IngeredientsResponse> ingredientsResponse, List<StepsResponse> stepsResponses, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientsResponse = ingredientsResponse;
        this.stepsResponses = stepsResponses;
        this.servings = servings;
        this.image = image;
    }

    protected RecipeModels(Parcel in) {
        id = in.readString();
        name = in.readString();
        ingredientsResponse = in.createTypedArrayList(IngeredientsResponse.CREATOR);
        stepsResponses = in.createTypedArrayList(StepsResponse.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeModels> CREATOR = new Creator<RecipeModels>() {
        @Override
        public RecipeModels createFromParcel(Parcel in) {
            return new RecipeModels(in);
        }

        @Override
        public RecipeModels[] newArray(int size) {
            return new RecipeModels[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngeredientsResponse> getIngredientsResponse() {
        return ingredientsResponse;
    }

    public void setIngredientsResponse(List<IngeredientsResponse> ingredientsResponse) {
        this.ingredientsResponse = ingredientsResponse;
    }

    public List<StepsResponse> getStepsResponses() {
        return stepsResponses;
    }

    public void setStepsResponses(List<StepsResponse> stepsResponses) {
        this.stepsResponses = stepsResponses;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredientsResponse);
        parcel.writeTypedList(stepsResponses);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
