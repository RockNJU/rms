package com.rms.action;

import java.util.List;
import java.util.UUID;

import com.rms.biz.CommonBiz;
import com.rms.biz.ProjectBiz;
import com.rms.biz.RiskBiz;
import com.rms.dto.UserDTO;
import com.rms.entity.Project;
import com.rms.entity.Risk;
import com.rms.entity.Riskitem;
import com.rms.listbean.RiskItemListBean;
import com.rms.listbean.RiskListBean;
import com.rms.util.publ.Time;
import com.rms.util.system.Constants;


public class RiskAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	RiskBiz riskBiz;
	CommonBiz commonBiz;
	
	private String id;
	private String riskid;
	private String projectid;
	private String userid;
	private String content;
	private String possibility;
	private String influence;
	private double shreshold;
	private String time;
	
	private String state;
	private String description;
	
	
	public String addRisk(){
		UserDTO user=(UserDTO)session.get(Constants.USERINFO);
		
		projectid=(String)session.get("projectId");
		
		Risk risk=new Risk();
		risk.setContent(content);
		risk.setInfluence(influence);
		risk.setPossibility(possibility);
		risk.setProjectid(projectid);
		risk.setShreshold(shreshold);
		risk.setId(UUID.randomUUID().toString());
		
		risk.setUserid(user.getUserid());
		risk.setUsername(user.getUsername());
		Time t=new Time();
		risk.setTime(t.getYMD());
		
		commonBiz.add(risk);
		
		
		
		refreshRiskPage();
		
		return "risk";
	}
	
	
	public String showRisk(){
		
		
		if(projectid==null){
			System.out.println("展示项目的风险时，项目id不能为空。");
		}
		
		this.getSession().setAttribute("projectId", projectid);
		
		refreshRiskPage();
		
		return "risk";
	}
	
	private void refreshRiskPage(){

		projectid=(String)session.get("projectId");
		
		Project project=(Project) commonBiz.get(Project.class, projectid);
		this.getSession().setAttribute("project", project);
		List<Risk> riskList=riskBiz.getRisk(projectid);
		
		RiskListBean bean=new RiskListBean();
		bean.setRiskList(riskList);
		this.getSession().setAttribute("riskList", bean);
	}
	
	public String showRiskItem(){
		if(riskid==null){
			System.out.println("展示风险详情时  itemid不能为空  。");
		}
		List<Riskitem> itemList=riskBiz.getRiskItem(riskid);
		
		RiskItemListBean bean=new RiskItemListBean();
		bean.setRiskitemList(itemList);
		
		
		this.getSession().setAttribute("itemlist", bean);
		
		Risk risk=(Risk) commonBiz.get(Risk.class, riskid);
		
		this.getSession().setAttribute("riskinfo", risk);
		
		return "riskItem";
	}
	
	private void refreshItemList(){
		
		Risk risk=(Risk)session.get("riskinfo");
		
		
		List<Riskitem> itemList=riskBiz.getRiskItem(risk.getId());
		RiskItemListBean bean=new RiskItemListBean();
		bean.setRiskitemList(itemList);
		
		this.getSession().setAttribute("itemlist", bean);
	}
	
	
	public String deleteRisk(){
		commonBiz.delete(Risk.class,riskid);
		refreshRiskPage();
		return "risk";
	}
	
public String addRiskItem(){
	
	UserDTO user=(UserDTO)session.get(Constants.USERINFO);
	
	Risk risk=(Risk)session.get("riskinfo");
		Riskitem item=new Riskitem();
		
		item.setDescription(description);
		item.setId(UUID.randomUUID().toString());
		item.setRiskid(risk.getId());
		item.setState(state);
		
		Time t=new Time();
		
		item.setUserid(user.getUserid());
		item.setTime(t.getYMD());
		
		item.setUsername(user.getUsername());
		commonBiz.add(item);
		
		refreshItemList();
		return "riskItem";
	}
	
	public String deleteRiskitem(){
		commonBiz.delete(Riskitem.class, id);
		refreshItemList();
		return "riskItem";
	}
	
	
	public RiskBiz getRiskBiz() {
		return riskBiz;
	}
	public void setRiskBiz(RiskBiz riskBiz) {
		this.riskBiz = riskBiz;
	}
	public String getRiskid() {
		return riskid;
	}
	public void setRiskid(String riskid) {
		this.riskid = riskid;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPossibility() {
		return possibility;
	}
	public void setPossibility(String possibility) {
		this.possibility = possibility;
	}
	public String getInfluence() {
		return influence;
	}
	public void setInfluence(String influence) {
		this.influence = influence;
	}
	public double getShreshold() {
		return shreshold;
	}
	public void setShreshold(double shreshhold) {
		this.shreshold = shreshhold;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public CommonBiz getCommonBiz() {
		return commonBiz;
	}

	public void setCommonBiz(CommonBiz commonBiz) {
		this.commonBiz = commonBiz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
