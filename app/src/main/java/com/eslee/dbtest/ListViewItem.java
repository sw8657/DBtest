package com.eslee.dbtest;

/**
 * Created by Administrator on 2017-02-20.
 */

public class ListViewItem {
    private int id_value;
    private String titleStr ;
    private String datetimeStr ;
    private int point_value;
    private String pointStr;

    public ListViewItem(int id, String title, String datetime, int point, String pointString){
        id_value = id;
        titleStr = title;
        datetimeStr = datetime;
        point_value = point;
        pointStr = pointString;
    }

    public void setId(int id){id_value=id;}
    public int getId(){
        return this.id_value;
    }

    public void setTitle(String title){
        titleStr = title;
    }
    public String getTitle(){
        return this.titleStr;
    }

    public void setDateTime(String datetime){
        datetimeStr = datetime;
    }
    public String getDateTime(){
        return this.datetimeStr;
    }

    public void setPoint(int point){
        point_value = point;
    }
    public int getPoint(){
        return this.point_value;
    }

    public void setPointStr(String pointString){
        pointStr = pointString;
    }
    public String getPointStr(){
        return this.pointStr;
    }
}
