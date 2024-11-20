package Model;

import java.util.ArrayList;

public class Food {
    private ArrayList<String> foodItems;
    private int numberFood;

    public Food() {
        foodItems = new ArrayList<>();
        numberFood = 0;
    }

    public void orderFood(String food) {
        foodItems.add(food);
        numberFood++;
        System.out.println("Đã đặt món: " + food);
    }

    public void cancelFood(String food) {
        if (foodItems.remove(food)) {
            numberFood--;
            System.out.println("Đã hủy món: " + food);
        } else {
            System.out.println("Món ăn không có trong danh sách đặt.");
        }
    }

    public ArrayList<String> getFoodItems() {
        return foodItems;
    }

    public int getNumberFood() {
        return numberFood;
    }
}