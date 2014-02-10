package com.sundayschool.beans;

import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.Map;

import static com.sundayschool.constants.Categories.*;
import static java.util.Arrays.asList;

public class StudentInfo {
    private int id;
    String firstName;
    String lastName;
    String church;
    String ssGroup;
    String categoryCode;
    String category;
    String venue;
    int judge1Marks;
    int judge2Marks;
    int judge3Marks;
    int totalMarks;

    public static Map<String, String> categoryMapLookup = new HashMap<String, String>();

    static {
//        categoryMapLookup.put(BIBLE_QUIZ, "BQ");
//        categoryMapLookup.put(DRAWING, "DR");
//        categoryMapLookup.put(ESSAY_WRITING, "ES");
//        categoryMapLookup.put(STORY_WRITING, "ST");
//        categoryMapLookup.put(POETRY, "PE");
        categoryMapLookup.put(GROUP_SONG_ENGLISH, "GE");
        categoryMapLookup.put(GROUP_SONG_MALAYALAM, "GM");
        categoryMapLookup.put(SOLO_SONG_ENGLISH, "SE");
        categoryMapLookup.put(SOLO_SONG_MALAYALAM, "SM");
        categoryMapLookup.put(ELOCUTION, "EL");
    }

    public StudentInfo() {
    }

    public StudentInfo(String firstName, String lastName, String church, String category, String ssGroup, String venue) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.church = church;
        this.category = category;
        this.ssGroup = ssGroup;
        this.venue = venue;
        categoryCode = categoryMapLookup.get(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChurch() {
        return church;
    }

    public void setChurch(String church) {
        this.church = church;
    }

    public String getSsGroup() {
        return ssGroup;
    }

    public void setSsGroup(String group) {
        this.ssGroup = group;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public int getJudge1Marks() {
        return judge1Marks;
    }

    public void setJudge1Marks(int judge1Marks) {
        this.judge1Marks = judge1Marks;
    }

    public int getJudge2Marks() {
        return judge2Marks;
    }

    public void setJudge2Marks(int judge2Marks) {
        this.judge2Marks = judge2Marks;
    }

    public int getJudge3Marks() {
        return judge3Marks;
    }

    public void setJudge3Marks(int judge3Marks) {
        this.judge3Marks = judge3Marks;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalMarks() {
        return judge1Marks + judge2Marks + judge3Marks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join(asList(firstName, lastName, church, categoryCode, ssGroup));
    }
}
