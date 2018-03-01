package com.example.dimitri.helpdeal.azureClasses.azureModels;

/**
 * Created by Dimitri on 15.12.2017.
 */

public class Documents {

    @com.google.gson.annotations.SerializedName("id")
    private String id;
    public String getDocumetId() {
        return id;
    }
    public void setDocumentId(String documentId) {
        this.id = documentId;
    }


    @com.google.gson.annotations.SerializedName("do_ref_id")
    private String do_ref_id;
    public String get_doRef_Id() {
        return do_ref_id;
    }
    public void set_doRef_id(String do_ref) {this.do_ref_id = do_ref; }

    @com.google.gson.annotations.SerializedName("do_link")
    private String do_link;
    public String getDo_link() {
        return do_link;
    }
    public void setDo_link(String link) {this.do_link = link;}

    @com.google.gson.annotations.SerializedName("do_typ")
    private String do_typ;
    public String getDo_typ() {
        return do_typ;
    }
    public void setDo_typ(String typ) {this.do_typ = typ;}

    public Documents(){}

    public Documents(String do_ref_id, String do_link, String typ){
        this.do_ref_id = do_ref_id;
        this.do_link = do_link;
        this.do_typ = typ;
    }

}
