package com.xhpower.education.system.core;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhpower.education.system.entity.Permission;
import com.xhpower.education.system.manager.PermissionManager;





public class ChainDefinitionSecionMetaSource implements
		FactoryBean<Ini.Section> {
	@Autowired
	private PermissionManager permissionManager;

	private String filterChainDefinitions;

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";
	public Section getObject() throws Exception {
		List<Permission> resources = permissionManager.selectList(null);
		Ini ini = new Ini();
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.get(Ini.DEFAULT_SECTION_NAME);
		for (Permission resource : resources) {
			if(!StringUtils.isEmpty(resource.getName()) && !StringUtils.isEmpty(resource.getPermission())){
				section.put(resource.getName(), MessageFormat.format(PREMISSION_STRING, resource.getPermission()));
			}
		}
		return section;
	}
	

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}


	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}

}
