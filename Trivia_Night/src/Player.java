package Trivia;

import java.util.ArrayList;

public class Player {
    private final String name;
    private int points;
    private ArrayList<Category> categories_answered;
    private ArrayList<question> questions_answered;
    private ArrayList<Category> categories_complete;
    private boolean has_lost = false;

    public Player(String name){
        this.name = name;
        this.points = 0;
        this.categories_answered = new ArrayList<>();
        this.questions_answered = new ArrayList<>();
        this.categories_complete = new ArrayList<>();
    }


    /**
     * Gets the name of the player.
     * @return The name.
     */
    public String getName(){
        return this.name;
    }


    /**
     * Gets the current points of the player.
     * @return int of current points.
     */
    public int getPoints(){
        return this.points;
    }


    /**
     * Adds points to the current Player.
     * @param points_added : Number of points to be added.
     *                     Note: This parameter is only here for potential game modes.
     */
    public void addPoints(int points_added){
        this.points += points_added;
    }


    /**
     * The player has lost, i.e has run out of categories and has been kicked from the game.
     */
    public void setHas_lost(){
        this.has_lost = true;
    }


    /**
     * Returns whether the Player has lost.
     * @return boolean whether the player has lost.
     */
    public boolean isHas_lost(){
        return this.has_lost;
    }


    /**
     * Adds a category to the list of answered categories and checks if it should be added to completed categories.
     * @param category The category to add.
     */
    public void addCategory(Category category, question q, boolean is_correct){
        this.categories_answered.add(category);
        if(completed_helper(q) >= 3 || is_correct)
            this.categories_complete.add(new Category(q.getCategory()));
    }


    /**
     * Returns the questions the player has answered.
     * @return ArrayList of answered questions.
     */
    public ArrayList<question> getQuestions_answered(){
        return this.questions_answered;
    }


    /**
     * Adds a answered question to the list.
     * @param q : The question that was answered.
     */
    public void addQuestion(question q){
        this.questions_answered.add(q);
    }


    /**
     * Finds the number of attempts a person has had for a single category.
     * @param question : The current question.
     * @return int : the number of attempts per category.
     */
    public int completed_helper(question question){
        int count = 0;
        for(question q : this.questions_answered){
            if(q.getCategory().equalsIgnoreCase(question.getCategory()))
                count++;
        }
        return count;
    }


    /**
     * Gets the completed categories.
     * @return ArrayList<Categories>
     */
    public ArrayList<Category> getCategories_complete(){
        return this.categories_complete;
    }


    @Override
    public String toString() {
        return getName() + " has: " + getPoints() + " points";
    }
}

