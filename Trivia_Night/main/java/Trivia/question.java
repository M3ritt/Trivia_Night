package Trivia;

import java.util.Random;
import java.util.ArrayList;

public class question {
    private final String question;
    private final String category;
    private final String answer;
    private final ArrayList<String> wrong_answers;

    public question(String question, String category, String answer, ArrayList<String> wrong_answers){
        this.question = question;
        this.category = category;
        this.answer = answer;
        this.wrong_answers = wrong_answers;
    }


    /**
     * Gets the question.
     * @return The question.
     */
    public String getQuestion(){
        return this.question;
    }

    /**
     * Gets the Category of the question.
     * @return The category.
     */
    public String getCategory(){
        return this.category;
    }


    /**
     * Gets the correct answer to the question.
     * @return The correct answer.
     */
    public String getAnswer(){
        return this.answer;
    }


    /**
     * Gets 3 wrong answers from the list of all possible wrong answers.
     * @return An Array of 3 wrong answers as possible choices.
     */
    public String[] getWrong_answers(){
        String[] answers = new String[3];
        Random number = new Random();
        String choice1 = "", choice2 = "", choice3 = "";
        while(choice1.equals(choice2) || choice2.equals(choice3) || choice1.equals(choice3)) {
            int pos_1 = number.nextInt(this.wrong_answers.size());
            choice1 = this.wrong_answers.get(pos_1);

            int pos_2 = number.nextInt(this.wrong_answers.size());
            choice2 = this.wrong_answers.get(pos_2);

            int pos_3 = number.nextInt(this.wrong_answers.size());
            choice3 = this.wrong_answers.get(pos_3);
        }
        answers[0] = choice1;
        answers[1] = choice2;
        answers[2] = choice3;
        return answers;
    }

    @Override
    public String toString() {
        return this.getQuestion() + " " + this.getCategory();
    }
}

