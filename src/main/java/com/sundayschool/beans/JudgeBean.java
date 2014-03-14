package com.sundayschool.beans;

import com.sundayschool.persistence.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class JudgeBean {
    String judge1 = "Judge 1";
    String judge2 = "Judge 2";
    String judge3 = "Judge 3";
    String judge4 = "Judge 4";
    String judge5 = "Judge 5";
    String judge6 = "Judge 6";
    String selectedJudge;
    String score;
    int id;

    public String saveScore() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("update StudentInfo set " + getUpdateColumn(selectedJudge) + " = :score where id = :id");
        query.setParameter("score", score);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        transaction.commit();
        session.close();

        if (result == 1) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Info message", selectedJudge.toUpperCase() + " marks for ID " + id + " updated to " + score);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return null;
        } else {
            FacesMessage facesMessage = new FacesMessage("Error updating score");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return null;
        }
    }

    private String getUpdateColumn(String selectedJudge) {
        if (selectedJudge.equals(judge1))
            return "judge1Marks";
        else if (selectedJudge.equals(judge2))
            return "judge2Marks";
        else if (selectedJudge.equals(judge3))
            return "judge3Marks";
        else if (selectedJudge.equals(judge4))
            return "judge4Marks";
        else if (selectedJudge.equals(judge5))
            return "judge5Marks";
        else
            return "judge6Marks";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelectedJudge() {
        return selectedJudge;
    }

    public void setSelectedJudge(String selectedJudge) {
        this.selectedJudge = selectedJudge;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getJudge1() {
        return judge1;
    }

    public void setJudge1(String judge1) {
        this.judge1 = judge1;
    }

    public String getJudge2() {
        return judge2;
    }

    public void setJudge2(String judge2) {
        this.judge2 = judge2;
    }

    public String getJudge3() {
        return judge3;
    }

    public void setJudge3(String judge3) {
        this.judge3 = judge3;
    }

    public String getJudge4() {
        return judge4;
    }

    public String getJudge5() {
        return judge5;
    }

    public String getJudge6() {
        return judge6;
    }
}
