package Trivia;

import java.util.*;
import java.util.concurrent.*;

public class Head_Head {
    private Player[] players;
    private final database db;
    private ArrayList<Category> categories;
    private final int points_to_win;
    private final int time;


    public Head_Head(Player[] players, database db, int points_to_win, int time){
        this.players = players;
        this.db = db;
        this.points_to_win = points_to_win;
        this.time = time;
    }


    /**
     * The load up and runner of the head to head trivia
     */
    public void start(){
        this.categories = db.getCategories();
        int turn = 1;
        while(!isGameOver()) {
            System.out.println("Turn " + turn);
            current_points();
            for(Player p : this.players) {
                playerTurn(p);
                if (isGameOver()) {
                    if(!p.isHas_lost())
                        System.out.println(p.getName() + " has won!");
                    break;
                }
            }
            turn++;
        }
        System.out.println("\nThe game is over!");
    }


    /**
     * The turn for a specific player: picking category choices and answering question.
     * @param p : the player that is currently up.
     */
    public void playerTurn(Player p){
        if(this.categories.size() - p.getCategories_complete().size() < this.points_to_win){
            System.out.println("Wow, " + p.getName() + " has ran out of categories and have been kicked from the game... " +
                    "\nThat is impressive!");
            kicker(p);
            return;
        }
        String[] choices = category_choices(p);
        System.out.println("\nPlayer " + p.getName() + " has the category choices:");
        int count = 1;
        for(String s : choices) {
            System.out.println(count + ". " + s);
            count++;
        }
        String category = category_picker(choices);
        if(answer_Question(category, p))
            p.addPoints(1);
    }


    /**
     * Method to remove a player from a game. They are removed if they cannot possibly win.
     * @param kicked : The player being kicked.
     */
    public void kicker(Player kicked){
        kicked.setHas_lost();
        List<Player> list = new ArrayList<>(Arrays.asList(this.players));
        list.remove(kicked);
        this.players = new Player[this.players.length-1];
        this.players = list.toArray(this.players);
    }


    /**
     * Helper method for player to pick a category.
     * @param choices: the list of categories to choose from.
     * @return The choice of category that was chosen.
     */
    public String category_picker(String[] choices){
        boolean isPicked = false;
        String choice, picked = "";
        Scanner sc = new Scanner(System.in);
        while(!isPicked){
            System.out.println("\nPick a category:");
            choice = sc.nextLine();
            picked = category_picker_helper(choices, choice);
            if(!picked.equals(""))
                isPicked = true;
        }
        return picked;
    }


    /**
     * Allows the user to pick a number instead of type out the category.
     * @param choices : The list of choices.
     * @param picked : What the user picked.
     * @return The String of the category chosen, based on number or full word.
     */
    public String category_picker_helper(String[] choices, String picked){
        System.out.println(picked);
        if (Arrays.asList(choices).contains(picked))
            return picked;
        else if(picked.equals("1"))
            return choices[0];
        else if(picked.equals("2"))
            return choices[1];
        else if(picked.equals("3"))
            return choices[2];
        else return "";
    }


    /**
     * Randomly generates three different choices of categories.
     * @return: Array of the three choices.
     */
    public String[] category_choices(Player p){
        String[] choices = new String[3];
        Random number = new Random();
        String choice1 = "", choice2 = "", choice3 = "";
        while(!is_valid_category(p, choice1, choice2, choice3)){
            int category_pos_1 = number.nextInt(this.categories.size());
            choice1 = this.categories.get(category_pos_1).getName();

            int category_pos_2 = number.nextInt(this.categories.size());
            choice2 = this.categories.get(category_pos_2).getName();

            int category_pos_3 = number.nextInt(this.categories.size());
            choice3 = this.categories.get(category_pos_3).getName();
        }
        choices[0] = choice1;
        choices[1] = choice2;
        choices[2] = choice3;
        return choices;
    }


    /**
     * Returns whether the categories are the same and whether the category has already been answered.
     * @param p : the Player.
     * @param choice1 : First category choice.
     * @param choice2 : Second category choice.
     * @param choice3 : Third category choice.
     * @return boolean based on whether the category choices are valid.
     */
    public boolean is_valid_category(Player p, String choice1, String choice2, String choice3){
        if(choice1.equals(choice2) || choice2.equals(choice3) || choice1.equals(choice3))
            return false;
        else return !already_answered(p,choice1,choice2,choice3) && !already_answered_question(p, choice1, choice2, choice3);
    }


    /**
     * Returns whether the Player has already answered the possible questions.
     * @param p : The Player.
     * @param choice1 : The first possible question.
     * @param choice2 : The second possible question.
     * @param choice3 : The third possible question.
     * @return : true if the question was already answered.
     */
    public boolean already_answered_question(Player p, String choice1, String choice2, String choice3){
        ArrayList<question> answered = p.getQuestions_answered();
        for(question q : answered){
            if(q.getQuestion().equalsIgnoreCase(choice1) || q.getQuestion().equalsIgnoreCase(choice2)
                    || q.getQuestion().equalsIgnoreCase(choice3))
                return true;
        }
        return false;
    }


    /**
     * Checks if a player has already answered this question.
     * @param p : the Player
     * @param category1 : the possible question being asked.
     * @param category2 : the possible question being asked.
     * @param category3 : the possible question being asked.
     * @return : true if the question was already answered.
     */
    public boolean already_answered(Player p, String category1, String category2, String category3){
        ArrayList<Category> answered = p.getCategories_complete();
        for(Category c : answered){
            if(c.getName().equals(category1) || c.getName().equals(category2)
                    || c.getName().equals(category3))
                return true;
        }
        return false;
    }


    /**
     * Method to show the player what the choices are and allows them the pick.
     * @param category: The category that was chosen.
     * @param p : The Player answering the question.
     * @return boolean whether the answer provided was correct or not.
     */
    public boolean answer_Question(String category, Player p){
        question q = db.find_category(category);
        System.out.println("\n" + q.getQuestion());
        System.out.println("The choices are: ");
        String[] choices = generate_answer_choices(q);
        int count = 1;
        for(String answer : choices) {
            System.out.println(count + ". " + answer);
            count++;
        }
        boolean is_true = check_answer(q, choices);
        p.addCategory(this.db.return_category(category), q, is_true);
        p.addQuestion(q);
        return is_true;
    }


    /**
     * Checks if the answer is correct and fast enough. Also adds the category to the players answered list.
     * @param q : The question that was asked.
     * @param choices : Array of the possible choices.
     * @return boolean whether the answer provided was correct or not.
     */
    public boolean check_answer(question q, String[] choices){
        String answer;
        boolean valid;
        Callable<String> sc = () -> new Scanner(System.in).nextLine();
        long start = System.currentTimeMillis();
        ExecutorService l = Executors.newFixedThreadPool(1);
        Future<String> g;
        g = l.submit(sc);
        done: while(System.currentTimeMillis() - start < this.time * 1000) {
            do {
                valid = true;
                if (g.isDone()) {
                    try {
                        answer = g.get();
                        return check_answer_helper(answer,choices,q);
                    } catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
                        g.cancel(true);
                        valid = false;
                        return false;
                    }
                }
            } while (!valid);
        }
        System.out.println("You took too long!");
        return false;
    }


    /**
     * Way to allow Player to enter letter correlated to answer.
     * @param answer : Given answer.
     * @param choices : All choices that were possible.
     * @param q : The original question.
     * @return boolean whether the typed out or number answer was correct.
     */
    public boolean check_answer_helper(String answer, String[] choices, question q){
        try {
            if (answer.equalsIgnoreCase(q.getAnswer()) ||
                    (choices[Integer.parseInt(answer) - 1].equals(q.getAnswer()))) {
                System.out.println("Correct!");
                return true;
            } else {
                System.out.println("Incorrect");
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Incorrect");
            return false;
        }
    }


    /**
     * Generates the answer choices.
     * @param q: the question being asked.
     * @return Array of choices.
     */
    public String[] generate_answer_choices(question q){
        String[] choices = new String[4];
        String[] wrongAnswers = q.getWrong_answers();
        Random number = new Random();
        int right_answer_pos = number.nextInt(4);
        int pos = 0;
        for(int i = 0; i < 4; i++){
            if(i == right_answer_pos)
                choices[i] = q.getAnswer();
            else {
                choices[i] = wrongAnswers[pos];
                pos++;
            }
        }
        return choices;
    }


    /**
     * Displays current points on board
     */
    public void current_points(){
        for(Player p : this.players){
            System.out.println(p);
        }
    }


    /**
     * Checks if the game is over based on points.
     * @return boolean whether the game is over or not.
     */
    public boolean isGameOver(){
        if(this.players.length <= 1)
            return true;
        for(Player p : this.players){
            if(p.getPoints() >= this.points_to_win)
                return true;
        }
        return false;
    }
}

