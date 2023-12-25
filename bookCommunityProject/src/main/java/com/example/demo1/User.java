package com.example.demo1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;

import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "Name")
	private String name;
	//@Column(name = "MyLibrary")
	//private Shelf myLibrarySet = null;
	//@Column(name  = "MyRequests")
	//private Set<Request> everyoneToMe = null;
	//@Column(name  = "SentRequests")
	//private Set<Request> meToOwner = null;
	//@Column(name = "AllowToRead")
	//private List<Book> allowBooks = null;	
	//@Column(name = "Role")
	//private Role role = null;
	@Column(name = "Login")
	private String login;
	@Column(name = "Password")
	private String password;
	@Column(name = "Mail")
	private String mail;
	@Enumerated(EnumType.STRING)
	@Column(name = "Role")
	private Role role;
	//@Column(name = "Roles")
	//@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"),
    //        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    //private List<Role> roles = new ArrayList<>();
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}
