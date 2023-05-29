package com.junit;

import api.PetsSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Проверка доступных методов для https://petstore.swagger.io/v2/pet/findByStatus")
public class CheckOptions {

    private final PetsSteps petsSteps = new PetsSteps();

    @Test
    @Owner("Artem Makhov")
    @DisplayName("Получение списка доступных методов")
    @Description("Отправика запроса OPTIONS и проверка ответа")
    public void getAllowMethods(){
       String allMethods = petsSteps.getAllowMethods("/pet/findByStatus");
       petsSteps.assertAllowMethods("OPTIONS,HEAD,GET", allMethods);
    }

    @Test
    @Owner("Artem Makhov")
    @DisplayName("Невалидный запрос с методом POST для https://petstore.swagger.io/v2/pet/findByStatus")
    @Description("Отправка не допустимого POST запроса для https://petstore.swagger.io/v2/pet/findByStatus и проверка ответа")
    public void postToFindByStatus(){
        petsSteps.postNotAllowMethods("/pet/findByStatus?status=sold");
    }
}
