package com.sundayschool.beans;

import com.sundayschool.constants.ChurchNames;
import com.sundayschool.persistence.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.*;

import static com.sundayschool.beans.StudentInfo.categoryMapLookup;
import static com.sundayschool.constants.Categories.*;
import static com.sundayschool.constants.Groups.*;

@ManagedBean
@SessionScoped
public class RegistrationBean {
    protected List<StudentInfo> studentInfoList = new ArrayList<StudentInfo>();
    private List<DistributionData> distributionDataList = new LinkedList<DistributionData>();
    protected List<StudentInfo> searchList = new ArrayList<StudentInfo>();
    protected String firstName;
    protected String lastName;
    protected String category;
    protected String group;
    protected String church;
    protected String venue;
    List<String> availableChurches;
    List<String> availableCategories;
    List<String> availableGroups;

    private PieChartModel pieModel = new PieChartModel();

    public String remove(StudentInfo studentInfo) {
        studentInfoList.remove(studentInfo);
        return null;
    }

    public String removeSearchItem(StudentInfo studentInfo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete StudentInfo where church = :church and ssGroup = :ssGroup and categoryCode = :categoryCode and firstName = :firstName and lastName = :lastName");
        query.setParameter("church", studentInfo.church);
        query.setParameter("ssGroup", studentInfo.ssGroup);
        query.setParameter("categoryCode", studentInfo.categoryCode);
        query.setParameter("firstName", studentInfo.firstName);
        query.setParameter("lastName", studentInfo.lastName);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
        searchList.remove(studentInfo);
        return null;
    }

    public String performSearch()
    {
        searchList.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String church = this.church.equals("All") ? "%" : this.church;
        String group = this.group.equals("All") ? "%" : this.group;
        String venue = this.venue.equals("All") ? "%" : this.venue;
        String category = this.category.equals("All") ? "%" : categoryMapLookup.get(this.category);
        Query query = session.createQuery("from StudentInfo where church like :church and ssGroup like :ssGroup and categoryCode like :categoryCode and venue like :venue");
        query.setParameter("church", church);
        query.setParameter("ssGroup", group);
        query.setParameter("categoryCode", category);
        query.setParameter("venue", venue);
        searchList.addAll(query.list());
        session.close();
        return null;
    }

    public String addAction() {

        StudentInfo studentInfo = new StudentInfo(this.firstName, this.lastName, this.church, this.category, this.group, this.venue);

        studentInfoList.add(studentInfo);
        return null;
    }

    public String confirm() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for (StudentInfo studentInfo : studentInfoList) {
            session.save(studentInfo);
        }
        transaction.commit();
        session.close();
        studentInfoList.clear();
        FacesMessage facesMessage = new FacesMessage("Successfully Added the following users");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return null;
    }

    public void displayData() {
        pieModel.clear();
        distributionDataList.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from StudentInfo where ssGroup like :ssGroup and categoryCode like :categoryCode");
        String group = this.group.equals("All") ? "%" : this.group;
        query.setParameter("ssGroup", group);
        String category = this.category.equals("All") ? "%" : categoryMapLookup.get(this.category);
        query.setParameter("categoryCode", category);
        List<StudentInfo> list = query.list();
        Map<String, Integer> results = new HashMap<String, Integer>();
        for (StudentInfo studentInfo : list) {
            Integer integer = results.get(studentInfo.church);
            if (integer == null) {
                results.put(studentInfo.church, 1);
            } else {
                results.put(studentInfo.church, integer + 1);
            }
        }

        for (Map.Entry<String, Integer> resultsEntry : results.entrySet()) {
            pieModel.set(resultsEntry.getKey(), resultsEntry.getValue());
            distributionDataList.add(new DistributionData(resultsEntry.getKey(), resultsEntry.getValue().toString()));
        }
        session.close();
    }

    public List<DistributionData> getDistributionDataList() {
        return distributionDataList;
    }

    public void setDistributionDataList(List<DistributionData> distributionDataList) {
        this.distributionDataList = distributionDataList;
    }

    public PieChartModel getPieModel() {
        if (pieModel.getData().isEmpty()) {
            pieModel.set("No Data", 100);
        }
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public List getStudentInfoList() {
        return studentInfoList;
    }

    public void setStudentInfoList(List studentInfoList) {
        this.studentInfoList = studentInfoList;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getChurch() {
        return church;
    }

    public void setChurch(String church) {
        this.church = church;
    }

    public List<StudentInfo> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<StudentInfo> searchList) {
        this.searchList = searchList;
    }

    public List<String> getAvailableChurches() {
        return Arrays.asList(ChurchNames.SMFB, ChurchNames.SGIR, ChurchNames.SMCOI,
                ChurchNames.STDL, ChurchNames.SPPL, ChurchNames.SGGL, ChurchNames.STJM, ChurchNames.STOK, ChurchNames.STCL);
    }

    public void setAvailableChurches(List<String> availableChurches) {
        this.availableChurches = availableChurches;
    }

    public List<String> getAvailableCategories() {
//        return Arrays.asList(BIBLE_QUIZ, DRAWING, ESSAY_WRITING, STORY_WRITING, POETRY);
        return Arrays.asList(GROUP_SONG_ENGLISH, GROUP_SONG_MALAYALAM, SOLO_SONG_ENGLISH, SOLO_SONG_MALAYALAM, ELOCUTION);
    }

    public void setAvailableCategories(List<String> availableCategories) {
        this.availableCategories = availableCategories;
    }

    public List<String> getAvailableGroups() {
        return Arrays.asList(GROUP_1, GROUP_2, GROUP_3, GROUP_4, GROUP_5, GROUP_6, GROUP_7);
    }

    public void setAvailableGroups(List<String> availableGroups) {
        this.availableGroups = availableGroups;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}