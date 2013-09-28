package com.sundayschool.beans;

import com.sundayschool.persistence.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.PieChartModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.*;

import static com.sundayschool.beans.StudentInfo.categoryMapLookup;

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

    // Church Constants
    public static String SMFB = "St. Mary's, Farmers Branch";
    public static String SMCOI = "St. Mary's COI, Carrollton";
    public static String SGIR = "St. George, Irving";
    public static String STDL = "St. Thomas, Dallas";
    public static String SPPL = "St. Paul's, Plano";
    public static String SGGL = "St. Gregorios, Garland";

    // Category Constants
    public static String BIBLE_QUIZ = "Bible Quiz";
    public static String STORY_WRITING = "Story Writing";
    public static String POETRY = "Poetry";
    public static String DRAWING = "Drawing";
    public static String ESSAY_WRITING = "Essay Writing";

    // Group Constants
    public static String GROUP_1 = "Group 1";
    public static String GROUP_2 = "Group 2";
    public static String GROUP_3 = "Group 3";
    public static String GROUP_4 = "Group 4";
    public static String GROUP_5 = "Group 5";
    public static String GROUP_6 = "Group 6";
    public static String GROUP_7 = "Group 7";

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
        String church = this.church.equals("All")? "%" : this.church;
        Query query = session.createQuery("from StudentInfo where church like :church and ssGroup = :ssGroup and categoryCode = :categoryCode");
        query.setParameter("church", church);
        query.setParameter("ssGroup", this.group);
        query.setParameter("categoryCode", categoryMapLookup.get(this.category));
        searchList.addAll(query.list());
        session.close();
        return null;
    }
    public String addAction() {

        StudentInfo studentInfo = new StudentInfo(this.firstName, this.lastName, this.church, this.category, this.group);

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

    public void displayData()
    {
        pieModel.clear();
        distributionDataList.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from StudentInfo where ssGroup = :ssGroup and categoryCode = :categoryCode");
        query.setParameter("ssGroup", this.group);
        query.setParameter("categoryCode", categoryMapLookup.get(this.category));
        List<StudentInfo> list = query.list();
        Map<String, Integer> results = new HashMap<String, Integer>();
        for (StudentInfo studentInfo : list) {
            Integer integer = results.get(studentInfo.church);
            if (integer == null)
            {
                results.put(studentInfo.church, 1);
            }
            else
            {
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

    public String getSMFB() {
        return SMFB;
    }

    public String getSMCOI() {
        return SMCOI;
    }

    public String getSGIR() {
        return SGIR;
    }

    public String getSTDL() {
        return STDL;
    }

    public String getSPPL() {
        return SPPL;
    }

    public String getSGGL() {
        return SGGL;
    }

    public String getBIBLE_QUIZ() {
        return BIBLE_QUIZ;
    }

    public void setBIBLE_QUIZ(String BIBLE_QUIZ) {
        this.BIBLE_QUIZ = BIBLE_QUIZ;
    }

    public String getSTORY_WRITING() {
        return STORY_WRITING;
    }

    public void setSTORY_WRITING(String STORY_WRITING) {
        this.STORY_WRITING = STORY_WRITING;
    }

    public String getPOETRY() {
        return POETRY;
    }

    public void setPOETRY(String POETRY) {
        this.POETRY = POETRY;
    }

    public String getDRAWING() {
        return DRAWING;
    }

    public void setDRAWING(String DRAWING) {
        this.DRAWING = DRAWING;
    }

    public String getESSAY_WRITING() {
        return ESSAY_WRITING;
    }

    public void setESSAY_WRITING(String ESSAY_WRITING) {
        this.ESSAY_WRITING = ESSAY_WRITING;
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

    public String getGROUP_1() {
        return GROUP_1;
    }

    public void setGROUP_1(String GROUP_1) {
        this.GROUP_1 = GROUP_1;
    }

    public String getGROUP_2() {
        return GROUP_2;
    }

    public void setGROUP_2(String GROUP_2) {
        this.GROUP_2 = GROUP_2;
    }

    public String getGROUP_3() {
        return GROUP_3;
    }

    public void setGROUP_3(String GROUP_3) {
        this.GROUP_3 = GROUP_3;
    }

    public String getGROUP_4() {
        return GROUP_4;
    }

    public void setGROUP_4(String GROUP_4) {
        this.GROUP_4 = GROUP_4;
    }

    public String getGROUP_5() {
        return GROUP_5;
    }

    public void setGROUP_5(String GROUP_5) {
        this.GROUP_5 = GROUP_5;
    }

    public String getGROUP_6() {
        return GROUP_6;
    }

    public void setGROUP_6(String GROUP_6) {
        this.GROUP_6 = GROUP_6;
    }

    public String getGROUP_7() {
        return GROUP_7;
    }

    public void setGROUP_7(String GROUP_7) {
        this.GROUP_7 = GROUP_7;
    }

    public List<StudentInfo> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<StudentInfo> searchList) {
        this.searchList = searchList;
    }
}