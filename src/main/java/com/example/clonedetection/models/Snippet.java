package com.example.clonedetection.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "snippet")
public class Snippet {

    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "Code is mandatory")
    @Length(max=15000)
    private String code;
    @ManyToOne
    @JoinColumn(
            name = "file_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    @NotNull(message = "File is mandatory")
    private File file;

    public Snippet(String code, File file) {
        this.code = code;
        this.file = file;
    }
}
