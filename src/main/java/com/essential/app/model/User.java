package com.essential.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_USER")
@Table(name = "TB_USER")
@DynamicUpdate
@NamedEntityGraph(name = "user.listSchedule",
        attributeNodes = {
            @NamedAttributeNode(value = "listSchedule"),
            @NamedAttributeNode("gender")
        }
)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Column(unique = true)
    @Email
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    private Gender gender;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Schedule> listSchedule;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
