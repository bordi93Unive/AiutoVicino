package it.unive.aiutovicino.model;

public class AnnuncioModel {
    public String id;
    public String title;
    public String description;
    public String id_category;
    public String date;
    public String time;
    public String place;
    public int partecipantsNumber;
    public boolean approved;
    public String idUser;

    public AnnuncioModel(){

    }

    public AnnuncioModel(String id, String idUser, String title, String id_category,String date,String time,String place, int partecipantsNumber, boolean approved){
        this.id = id;
        this.title = title;
        this.description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        this.id_category = id_category;
        this.date = date;
        this.time = time;
        this.place = place;
        this.partecipantsNumber = partecipantsNumber;
        this.approved = approved;
        this.idUser = idUser;
    }
}
