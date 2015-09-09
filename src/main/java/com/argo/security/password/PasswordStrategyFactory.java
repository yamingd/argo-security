package com.argo.security.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("passwordStrategyFactory")
public class PasswordStrategyFactory {

	@Autowired
	@Qualifier("defaultPasswordService")
	private PasswordStrategy defaultStrategy;

	private List<PasswordStrategy> impls = new ArrayList<PasswordStrategy>();
	
	/**
	 * 按照用户的标识来采用不同的密码服务.
	 * @param modeId
	 * @return
	 */
	public PasswordStrategy get(int modeId){
		for (PasswordStrategy item : impls) {
			if(modeId == item.getModeId()){
				return item;
			}
		}
		return defaultStrategy;
	}
	
	public void add(PasswordStrategy item){
		this.impls.add(item);
	}

	/**
	 * @return the defaultService
	 */
	public PasswordStrategy getDefault() {
		return defaultStrategy;
	}
}
