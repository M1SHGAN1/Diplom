package com.junit;

import api.Data.PetDataGeneration;
import api.Models.PetsData;
import api.PetsSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Проверки по удалению питомца")
public class DeletePet {

    private final PetsSteps petsSteps = new PetsSteps();
    private final PetsData dataOfPet = PetDataGeneration.generateDataPet("sold");

    @Test
    @Owner("Artem Makhov")
    @DisplayName("Удаление существующего питомца")
    @Description("Удаление существующего питомца. Метод DELETE. Проверка тела ответа")
    public void deleteExistedPet(){

        petsSteps.crateNewPet(dataOfPet);
        Response res = petsSteps.deletePetById(dataOfPet.getId(), 200);
        petsSteps.assertBadRequestBody(res,"unknown",dataOfPet.getId().toString());

    }

    @Test
    @Owner("Artem Makhov")
    @DisplayName("Удаление несуществующего питомца")
    @Description("Удаление несуществующего питомца. Метод DELETE. Проверка тела ответа")
    public void deleteNotExistedPet(){

        Response res = petsSteps.deletePetById(dataOfPet.getId(), 404);
        petsSteps.assertBadRequestBody(res,"error","Pet not found");

    }
}
