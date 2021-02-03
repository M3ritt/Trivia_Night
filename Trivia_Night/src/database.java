package Trivia;

import java.util.ArrayList;

public class database {
    private final ArrayList<Category> categories;
    private final ArrayList<question> questions;

    public database(){
        this.categories = new ArrayList<>();
        this.questions = new ArrayList<>();
    }

    /**
     * @return the ArrayList of categories
     */
    public ArrayList<Category> getCategories(){
        return this.categories;
    }


    /**
     * Adds a new question to the database and adds it to the list of categories if not already in.
     * @param q: the question being added to the database
     */
    public void fill_database(question q){
        if(!this.questions.contains(q))
            this.questions.add(q);
        Category current_category = return_category(q.getCategory());
        if(current_category == null) {
            Category c = new Category(q.getCategory());
            c.addQuestion(q);
            this.categories.add(c);
        } else {
            current_category.addQuestion(q);
        }
    }


    /**
     * Searches for a specific category and returns a random question from it.
     * @param category : the name of the category to search for.
     * @return the question or null
     */
    public question find_category(String category){
        for(Category c : this.categories){
            if(c.getName().equals(category))
                return c.generate_question();
        }
        return null;
    }


    /**
     * Returns the category based on name.
     * @param category_name : the name of the category.
     * @return the Category or null.
     */
    public Category return_category(String category_name){
        for(Category c : this.categories){
            if(c.getName().equals(category_name))
                return c;
        }
        return null;
    }


    /**
     * Prints out the names of all categories.
     */
    public void get_all_categories(){
        for(Category c : this.categories)
            System.out.println(c);
    }


    @Override
    public String toString() {
        return this.categories.size() + " categories with " + this.questions.size() + " total questions";
    }
}

