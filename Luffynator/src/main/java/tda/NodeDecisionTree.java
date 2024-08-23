/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tda;

/**
 *
 * @author asala
 */
public class NodeDecisionTree {
    private String content;
    private DecisionTree yesBranch;
    private DecisionTree noBranch;

    public NodeDecisionTree(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DecisionTree getYesBranch() {
        return yesBranch;
    }

    public void setYesBranch(DecisionTree yesBranch) {
        this.yesBranch = yesBranch;
    }

    public DecisionTree getNoBranch() {
        return noBranch;
    }

    public void setNoBranch(DecisionTree noBranch) {
        this.noBranch = noBranch;
    }
}