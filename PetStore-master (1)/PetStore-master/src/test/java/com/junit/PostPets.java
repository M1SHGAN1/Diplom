package com.junit;

import api.Data.InvalidData;
import api.Data.PetDataGeneration;
import api.Models.PetsData;
import api.PetsSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Проверки создания нового питомца")
public class PostPets {

    private final PetsSteps petsSteps = new PetsSteps();
    private final PetsData dataOfPet = PetDataGeneration.generateDataPet("sold");

    @Test
    @Owner("Artem Makhov")
    @DisplayName("Добавление нового питомца с валидными данными")
    @Description("Добавление нового питомца с валидными данными. Метод POST. Проверка тела ответа и поиск новой записи")
    public void postValidNewPet(){

        PetsData body = petsSteps.crateNewPet(dataOfPet);
        petsSteps.assertPetBody(body,dataOfPet);
        PetsData petById = petsSteps.getPetById(body.getId());
        petsSteps.assertPetBody(petById, dataOfPet);

    }

    @Test
    @Owner ("Artem Makhov")
    @DisplayName("Добавление нового питомца с невалидными данными")
    @Description("Добавление нового питомца с невалидными данными. Метод POST. Проверка тела ответа.")
    public void postInvalidNewPet(){

        Response res = petsSteps.postBadPet(InvalidData.INCORRECT_JSON_BODY);

        petsSteps.assertBadRequestBody(res,"error","bad input");

    }

}
