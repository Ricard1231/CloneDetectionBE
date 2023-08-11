package com.example.clonedetection.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "snippet_pair")
public class SnippetPair {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "snippet1_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    @NotNull(message = "Snippet1 is mandatory")
    private Snippet snippet1;

    @ManyToOne
    @JoinColumn(
            name = "snippet2_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    @NotNull(message = "Snippet2 is mandatory")
    private Snippet snippet2;
}
