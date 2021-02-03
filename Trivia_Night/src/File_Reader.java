package Trivia;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class File_Reader {
    private final database db;

    public File_Reader(database db){
        this.db = db;
    }

    /**
     * Gets the database.
     * @return The database.
     */
    public database getDb(){
        return this.db;
    }

    /**
     * Reads the file by each line and calls decipher_line to format the questions.
     */
    public void read_file(){
        try(BufferedReader br = new BufferedReader(new FileReader(new File("../../questions.txt")))){
            String line;
            while ((line = br.readLine()) != null){
                decipher_line(line);
            }
        } catch (IOException e) {
            System.out.print("");
        }
    }


    /**
     * Takes the line from the file and creates the question based on the line.
     * @param line The line from the file.
     */
    public void decipher_line(String line){
        List<String> qp = Arrays.asList(line.split(":"));
        String quest = qp.get(0);
        String category = qp.get(1);
        String answer = qp.get(2);
        String wrong = qp.get(3);

        ArrayList<String> wrong_answers = new ArrayList<>();
        String[] wa = wrong.split(",");
        Collections.addAll(wrong_answers, wa);
        question q = new question(quest, category, answer, wrong_answers);
        this.db.fill_database(q);
    }
}

