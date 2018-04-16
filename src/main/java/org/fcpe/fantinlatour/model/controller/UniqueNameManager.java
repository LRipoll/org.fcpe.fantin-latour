package org.fcpe.fantinlatour.model.controller;

public interface UniqueNameManager {

	boolean exists(String name);

	boolean isValidName(String name);

}