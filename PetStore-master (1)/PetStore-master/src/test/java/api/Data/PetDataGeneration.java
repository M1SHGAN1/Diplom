package api.Data;

import api.Models.Category;
import api.Models.PetsData;
import api.Models.Tag;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PetDataGeneration {
    public static PetsData generateDataPet(String status) {
         PetsData petsData = new PetsData();
         Category category = new Category();
         Tag tag = new Tag();

         Long rndLong =ThreadLocalRandom.current().nextLong(2030009999);

         petsData.setId(rndLong);
         petsData.setName(RandomStringUtils.randomAlphabetic(8));
         petsData.setPhotoUrls(Arrays.asList(RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8)));
         category.setId(rndLong);
         category.setName(RandomStringUtils.randomAlphabetic(8));
         petsData.setCategory(category);
         tag.setId(new Random().nextInt(2030099));
         tag.setName(RandomStringUtils.randomAlphabetic(8));
         petsData.setTags(Arrays.asList(tag));
         petsData.setStatus(status);

        return petsData;
    }

}


