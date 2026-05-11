package com.eweb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "favorite")
public class favorite {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	@Column
	    private Long productId;
	    @Column
	    private Long customerId;
	    @Column
	    private LocalDateTime createdAt;
}
