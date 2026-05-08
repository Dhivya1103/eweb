package com.eweb.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
public class Admin {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column
	    private String fullName;

	    @Column
	    private String email;

	    @Column
	    private String mobileNumber;

	    @Column
	    private String password;

	   @Column
	    private Long  roleId;
	   @Column
	   private String status;
	   @Column
	   private String otp;
	   @Column
	   private LocalDateTime expiryTime;
}
