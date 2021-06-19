package com.aharoo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.aharoo.security.ApplicationUserPermission.*;


public enum ApplicationUserRole {
	USER(Sets.newHashSet()),
	MODERATOR(Sets.newHashSet(READ)),
	ADMIN(Sets.newHashSet(READ, WRITE));


	private final Set<ApplicationUserPermission> permissions;

	ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermission> getPermissions() { return permissions; }

	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> grantedAuthorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return grantedAuthorities;
	}

}
