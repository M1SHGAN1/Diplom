package api;

import api.Models.PetsData;
import api.Models.ResponseBody;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetsSteps extends PetsService {

    @Step ("Получить питомцев по status = {status}")
    @Attachment
    public List<PetsData> getPetByStatus(String status) {
        Response response = getPetsByStatus(status);
        assertSCode(200, response);
        return response.jsonPath().getList(".", PetsData.class);
    }

    @Step ("Получить питомца по ID")
    public PetsData getPetById (Long petId){
        Response response = getPetByIds(petId);
        assertSCode(200,response);
        assertHeaders(response);
        return response.body().as(PetsData.class);
    }

    @Step ("Получение списка статусов для всех питомцев")
    @Attachment
    public List<String> getStatuses(List<PetsData> pets) {
        List<String> statuses = new ArrayList<>();
        pets.forEach(n-> statuses.add(n.getStatus()));
        return statuses;
    }

    @Step ("Добавление нового питомца метод POST с невалидным body")
    public Response postBadPet (Object pet){
        Response response = postNewPet(pet);
        assertSCode(400,response);
        return response;
    }

    @Step ("Отправить Options запрос  и получить список доступных методов для {path}")
    @Attachment
    public String getAllowMethods (String path){
        Response response = sendOptionsReq(path);
        assertSCode(204, response);
        return response.getHeader("Allow");
    }

    @Step ("Добавление нового питомца метод POST")
    public PetsData crateNewPet (PetsData pet){
        Response response = postNewPet(pet);
        assertSCode(200,response);
        assertHeaders(response);
        Allure.addAttachment("CreatedPet",response.body().as(PetsData.class).toString());
        return response.body().as(PetsData.class);
    }

    @Step ("Отправка недопустимого POST запроса и проверка статуса ответа")
    public void postNotAllowMethods (String path){
        Response response = notAllowedPost(path);
        assertSCode(405, response);
    }
    @Step ("Удаление питомца по ID")
    public Response deletePetById (Long petId, Integer statusCode){
        Response response = deletePet(petId);
        assertSCode(statusCode,response);
        return response;
    }


    @Step ("Проверка что поиск с несколькими статусами возвращает записи со статусом {status}")
    public void assertPetsSeveralStatus(List<String> statuses, String status){
        assertThat("Check that list of statuses contains status = {status}", statuses, hasItem(status));
    }

    @Step ("Проверка что статус код ответа {expectedStatusCode}")
    public void assertSCode(Integer expectedStatusCode, Response response) {
        assertThat("Check that response status code equals {expectedStatusCode}", response.statusCode(), equalTo(expectedStatusCode));
  }
    @Step ("Проверка заголовков ответа")
    public void assertHeaders(Response response) {
        assertThat("Check header access-control-allow-headers",response.getHeader("access-control-allow-headers"), equalTo("Content-Type, api_key, Authorization"));
        assertThat("Check header access-control-allow-methods",response.getHeader("access-control-allow-methods"), equalTo("GET, POST, DELETE, PUT"));
        assertThat("Check header content-type",response.getHeader("content-type"), equalTo("application/json"));
    }

    @Step ("Проверка доступных методов")
    public void assertAllowMethods (String expectedMethods, String allowMethods){
        assertThat("Check that received methods are correct", allowMethods, equalTo(expectedMethods));
    }

    @Step ("Проверка типа и сообщения ответа для невалидного запроса")
    public void assertBadRequestBody (Response response, String expectedType, String expectedMessage){
            assertThat("Check that response body is not empty",response.body().asString().length(),greaterThan(0));
            String resType = response.body().as(ResponseBody.class).getType();
            String resMessage = response.body().as(ResponseBody.class).getMessage();
            assertThat("Check type of response", resType, equalTo(expectedType));
            assertThat("Check type of response", resMessage, equalTo(expectedMessage));

    }

    @Step ("Проверка тела ответа / питомца")
    public void assertPetBody (PetsData body, PetsData pet){
        Assertions.assertThat(body).as("Check that response body is equal request body").isEqualToComparingFieldByFieldRecursively(pet);
    }

    @Step ("Проверка что все найденные питомцы имеют корректный статус {status}")
    public PetsSteps assertPetsStatus(List<String> statuses, List status){
        assertThat("Check that status is equal {status} for every Items", statuses, everyItem(is(in(status))));
        return this;
    }

}
