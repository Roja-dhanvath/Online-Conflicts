package com.example.rojad.projectlogin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rojad.projectlogin.model.Answer;
import com.example.rojad.projectlogin.model.Contact;
import com.example.rojad.projectlogin.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rojad on 11/27/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact.db";
    private static final String TABLE_NAME_USERS = "Contacts";
    private static final String TABLE_NAME_QUESTIONS = "Questions";
    private static final String TABLE_NAME_ANSWERS = "Answers";
    private static final String COLUMN_ROW_ID = "rowid";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASS = "pass";
    private static final String COLUMN_TYPE = "type";

    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_POSTBY = "postBy";

    private static final String COLUMN_QUESTION_ID = "questionId";
    private static final String COLUMN_ANSWER = "answer";
    private static final String COLUMN_ANSWERBY = "answerBy";
    SQLiteDatabase db;

    private static final String CREATE_USERS_TABLE = "create table " + TABLE_NAME_USERS + " ( name text not null, email text UNIQUE not null, uname text primary key UNIQUE not null, pass text not null,type int not null);";

    private static final String CREATE_QUESTIONS_TABLE = "Create table " + TABLE_NAME_QUESTIONS + " (question text not null, postBy int not null);";

    private static final String CREATE_ANSWERS_TABLE = "Create table " + TABLE_NAME_ANSWERS + "( questionId int not null,answer text not null, answerBy int not null)";

    private static final String CHECK_CONTACT = "Select rowid,* from contacts where email = \"%s\" AND uname = \"%s\"";

    private static final String VALIDATE_CONTACT = "Select rowid,* from contacts where pass = \"%s\" AND uname = \"%s\"";

    private static final String GET_QUESTIONS_POSTED_BY_USER = "Select rowid,* from " + TABLE_NAME_QUESTIONS + " where " + COLUMN_POSTBY + " = %d order by rowid desc";

    private static final String GET_QUESTIONS_FOR_ADMIN = "Select rowid,* from " + TABLE_NAME_QUESTIONS + " where rowid NOT IN (Select " + COLUMN_QUESTION_ID + " from " + TABLE_NAME_ANSWERS + " where " + COLUMN_ANSWERBY + " = %d) order by rowid desc";

    public static final String GET_ADMIN_ANSWERS_BY_QUESTION = "Select rowid,* from " + TABLE_NAME_ANSWERS + " where " + COLUMN_QUESTION_ID + " = %d";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_ANSWERS_TABLE);
        this.db = db;
    }

    public void insertAdmins() {
        Contact admin1 = new Contact();
        admin1.name = "Admin1";
        admin1.email = "admin1@gmail.com";
        admin1.uname = "admin1";
        admin1.password = "admin1";
        admin1.type = Contact.CONTACT_TYPE_ADMIN;
        insertContact(admin1);
        admin1.name = "Admin2";
        admin1.email = "admin2@gmail.com";
        admin1.uname = "admin2";
        admin1.password = "admin2";
        admin1.type = Contact.CONTACT_TYPE_ADMIN;
        insertContact(admin1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME_USERS;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS" + TABLE_NAME_QUESTIONS;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS" + TABLE_NAME_ANSWERS;
        db.execSQL(query);
        this.onCreate(db);

    }

    //Methods for USERS TABLE
    public long insertContact(Contact c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, c.name);
        values.put(COLUMN_EMAIL, c.email);
        values.put(COLUMN_UNAME, c.uname);
        values.put(COLUMN_PASS, c.password);
        values.put(COLUMN_TYPE, c.type);
        return db.insert(TABLE_NAME_USERS, null, values);

    }

    public boolean IsContactExist(Contact contact) {
        db = this.getWritableDatabase();
        String query = String.format(CHECK_CONTACT, contact.email, contact.uname);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public Contact ValidateContact(Contact contact) {

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format(VALIDATE_CONTACT, contact.password, contact.uname), null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Contact validContact = new Contact();
            validContact.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ROW_ID));
            validContact.email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            validContact.uname = cursor.getString(cursor.getColumnIndex(COLUMN_UNAME));
            validContact.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            validContact.type = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            return validContact;
        }
        return null;

    }

    public Contact getUserDetails(int userId) {
        db = this.getReadableDatabase();
        System.out.println("Select rowid,* from " + TABLE_NAME_USERS + " where rowid = " + userId);
        Cursor cursor = db.rawQuery("Select rowid,* from " + TABLE_NAME_USERS + " where rowid = " + userId, null);
        cursor.moveToFirst();
        Contact contact = new Contact();
        if (cursor.getCount() > 0) {
            do {

                contact.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ROW_ID));
                contact.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                contact.uname = cursor.getString(cursor.getColumnIndex(COLUMN_UNAME));
                contact.email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));

            } while (cursor.moveToNext());
        }

        return contact;
    }

    //Methods for Questions Table
    public long insertQuestion(Question question) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question.question);
        values.put(COLUMN_POSTBY, question.postedBy);
        return db.insert(TABLE_NAME_QUESTIONS, null, values);
    }

    public List<Question> getQuestionsByAdmin(Contact contact) {
        System.out.println(String.format(GET_QUESTIONS_FOR_ADMIN, contact.id));
        Cursor cursor = db.rawQuery(String.format(GET_QUESTIONS_FOR_ADMIN, contact.id), null);
        cursor.moveToFirst();
        List<Question> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.questionId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROW_ID));
                    question.question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
                    question.postedBy = cursor.getInt(cursor.getColumnIndex(COLUMN_POSTBY));
//                    question.user = getUserDetails(question.postedBy);
                    list.add(question);
                } while (cursor.moveToNext());
            }
            cursor.close();
            System.out.println(new Gson().toJson(list));
            for (Question question : list) {
                question.user = getUserDetails(question.postedBy);
            }
        }
        System.out.println(new Gson().toJson(list));
        return list;

    }

    public List<Question> getQuestionsBy(Contact contact) {
        System.out.println(String.format(GET_QUESTIONS_POSTED_BY_USER, contact.id));
        Cursor cursor = db.rawQuery(String.format(GET_QUESTIONS_POSTED_BY_USER, contact.id), null);
        cursor.moveToFirst();
        List<Question> list = new ArrayList<>();
        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do { //fetching list of questions posted by a user
                    Question question = new Question();
                    question.questionId = cursor.getInt(cursor.getColumnIndex(COLUMN_ROW_ID));
                    question.question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
                    question.postedBy = contact.id;
                    list.add(question);
                } while (cursor.moveToNext());
            }
            cursor.close();
            for (Question question : list) { //fetching list of answers provided by counselors for each question
                question.answers = getAnswers(question.questionId);
            }
        }
        System.out.println(new Gson().toJson(list));
        return list;
    }

    //Methods for Answers Table
    public long insertAnswer(Answer answer) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_ID, answer.questionId);
        values.put(COLUMN_ANSWER, answer.answer);
        values.put(COLUMN_ANSWERBY, answer.answerBy);
        return db.insert(TABLE_NAME_ANSWERS, null, values);
    }

    public List<Answer> getAnswers(int questionId) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format(GET_ADMIN_ANSWERS_BY_QUESTION, questionId), null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ArrayList<Answer> answers = new ArrayList<>();
            do {
                Answer answer = new Answer();
                answer.questionId = questionId;
                answer.answer = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER));
                answer.answerBy = cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWERBY));
                answers.add(answer);
            } while (cursor.moveToNext());
            cursor.close();
            return answers;
        }
        return new ArrayList<>();
    }


}



