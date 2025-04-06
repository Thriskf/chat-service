package org.elteq.logic.dob.db

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.json.bind.annotation.JsonbTransient
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.elteq.logic.users.models.Users
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import java.time.Month

@Entity
@Table(
    name = "tbl_dob",
    indexes = [
        Index(name = "index_user_id", columnList = "user_id" ),
    ]
)
class DOB : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @NotNull
    @Column(name = "day")
    @Min(1)
    @Max(31)
    var day: Int? = null

    @NotNull
    @Column(name = "month")
    var month: Month? = Month.JANUARY

    @NotNull
    @Column(name = "year")
    @Min(1600)
    var year: Int? = null

    @JsonIgnore
    @JsonbTransient
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: Users? = null

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null

    override fun toString(): String {
        return "DOB(id=$id, day=$day, month=$month, year=$year, createdOn=$createdOn, updatedOn=$updatedOn)"
    }

}