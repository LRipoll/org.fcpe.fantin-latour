package org.fcpe.fantinlatour.model;

import org.fcpe.fantinlatour.dao.files.xstream.EncryptedStringConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("Etablissement")
public class Etablissement {

	@XStreamAlias("nom")
	@XStreamConverter(EncryptedStringConverter.class)
	@XStreamAsAttribute
	private String nom;
	@XStreamAlias("type")
	@XStreamAsAttribute
	private TypeEtablissement typeEtablissement;

	public Etablissement(String nom, TypeEtablissement typeEtablissement) {
		super();
		this.nom = nom;
		this.typeEtablissement = typeEtablissement;

	}



	public String getNom() {
		return nom;
	}

	public TypeEtablissement getTypeEtablissement() {
		return typeEtablissement;
	}

	public void setNom(String nom) {
		this.nom = nom;
		
	}

}
