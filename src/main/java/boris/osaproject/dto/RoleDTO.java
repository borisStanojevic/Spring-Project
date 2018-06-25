package boris.osaproject.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import boris.osaproject.entity.Role;

public class RoleDTO implements Serializable {
	
	public static Set<RoleDTO> convert(Set<Role> roleEntitySet) {
		Set<RoleDTO> roleDTOSet = new HashSet<>();
		Iterator<Role> iterator = roleEntitySet.iterator();
		while (iterator.hasNext()) {
			Role role = (Role) iterator.next();
			RoleDTO roleDTO = new RoleDTO(role);
			roleDTOSet.add(roleDTO);
		}
		return roleDTOSet;
	}
	
	private String authority;
	
	private RoleDTO(String authority) {
		this.authority = authority;
	}
	
	public RoleDTO() {}
	
	public RoleDTO(Role role)
	{
		this(role.getAuthority());
	}

	public String getRole() {
		return authority;
	}

	public void setRole(String authority) {
		this.authority = authority;
	}

}
