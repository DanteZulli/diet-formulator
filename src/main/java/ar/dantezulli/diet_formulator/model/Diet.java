package ar.dantezulli.diet_formulator.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "diet")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "animal_profile_id", nullable = false)
    private AnimalProfile animalProfile;

    @ToString.Exclude
    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<DietItem> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addItem(DietItem item) {
        items.add(item);
        item.setDiet(this);
    }

    public void removeItem(DietItem item) {
        items.remove(item);
        item.setDiet(null);
    }
}
