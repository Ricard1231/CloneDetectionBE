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
@Table(name = "user_snippet")
public class UserSnippet {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    @JsonIgnore
    @NotNull(message = "User is mandatory")
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "snippet_pair_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    @NotNull(message = "SnippetPair is mandatory")
    private SnippetPair snippetPair;

    @NotNull(message = "Type is mandatory")
    private Integer type;
}
