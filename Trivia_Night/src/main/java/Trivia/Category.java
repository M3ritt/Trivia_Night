package Trivia;

import java.util.ArrayList;
import java.util.Random;

public class Category {
    private final String name;
    private ArrayList<question> questions;

    public Category(String name){
        this.questions = new ArrayList<>();
        this.name = name;
    }


    /**
     * Gets the name
     * @return The name.
     */
    public String getName(){
        return this.name;
    }


    /**
     * Adds a question to the category.
     * @param q : the question.
     */
    public void addQuestion(question q){
        this.questions.add(q);
    }


    /**
     * Picks a question, at random, from the category.
     * @return the question.
     */
    public question generate_question(){
        Random number = new Random();
        int pos = number.nextInt(this.questions.size());
        return this.questions.get(pos);
    }

    @Override
    public String toString() {
        return this.name + " has " + this.questions.size() + " many questions.";
    }
}

