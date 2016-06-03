package com.rajesh.custominfowindowwithcustommarkeringooglemap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rajesh on 5/23/2016.
 */
public class MapViewResponse {

    private String UserName;
    private String UserImageUrl;
    private LatLng latlng;
    private String Address;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
