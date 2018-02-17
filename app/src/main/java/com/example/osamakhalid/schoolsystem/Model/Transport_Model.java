package com.example.osamakhalid.schoolsystem.Model;

/**
 * Created by Osama Khalid on 2/1/2018.
 */

public class Transport_Model {
    String vanNum;
    String route;
    public Transport_Model(){}
    public Transport_Model(String vanNum,String route){
        this.vanNum=vanNum;
        this.route=route;
    }
    public String getRoute() {
        return route;
    }

    public String getVanNum() {
        return vanNum;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setVanNum(String vanNum) {
        this.vanNum = vanNum;
    }
}
