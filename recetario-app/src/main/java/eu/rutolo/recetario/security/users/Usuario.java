package eu.rutolo.recetario.security.users;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import eu.rutolo.recetario.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity @Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements UserDetails {
	
	@Id @EqualsAndHashCode.Include
	private String username;

	private String password;
	
	@EqualsAndHashCode.Include
	private boolean enabled;
	
	private boolean rolUser;

	private boolean rolAdmin;
	
	@Override
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> auts = new HashSet<>();
		if (this.rolUser) {
			auts.add(new SimpleGrantedAuthority(Constants.ROL_USER));
		}
		if (this.rolAdmin) {
			auts.add(new SimpleGrantedAuthority(Constants.ROL_ADMIN));
		}
		return auts;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isEnabled();
	}

}
