package test;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class CourierGenerator {
    public static Courier getRandom(){
        final String login = RandomStringUtils.randomAlphabetic(8);
        final String password = RandomStringUtils.randomAlphabetic(8);
        final String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(login, password, firstName);
    }
    public static Courier getRandomWithoutLogin(){
        final String password = RandomStringUtils.randomAlphabetic(8);
        final String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(null, password, firstName);
    }
    public static Courier getRandomWithoutPassword(){
        final String login = RandomStringUtils.randomAlphabetic(8);
        final String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(login, null, firstName);
    }

}
