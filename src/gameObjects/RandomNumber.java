package gameObjects;

public class RandomNumber {
    public static int randomizer(int min, int max) {

        return (int) (Math.random() * (max - min + 1) + min);
    }
}
