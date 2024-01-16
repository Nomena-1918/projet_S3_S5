package org.example.demo.models;

public class Voyage {
    private Long id;
    private Long idCategorieLieu;
    private Long idTypeDuree;
    private Long idBouquet;

    public Voyage(Long id, Long idCategorieLieu, Long idTypeDuree, Long idBouquet) {
        this.id = id;
        this.idCategorieLieu = idCategorieLieu;
        this.idTypeDuree = idTypeDuree;
        this.idBouquet = idBouquet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategorieLieu() {
        return idCategorieLieu;
    }

    public void setIdCategorieLieu(Long idCategorieLieu) {
        this.idCategorieLieu = idCategorieLieu;
    }

    public Long getIdTypeDuree() {
        return idTypeDuree;
    }

    public void setIdTypeDuree(Long idTypeDuree) {
        this.idTypeDuree = idTypeDuree;
    }

    public Long getIdBouquet() {
        return idBouquet;
    }

    public void setIdBouquet(Long idBouquet) {
        this.idBouquet = idBouquet;
    }
}
