package hh.excellence.question.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="UUIDs")
public class MyUUID {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Size(max=36)
    @Column(name="uuid")
    private String uuid;

    public MyUUID() {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }
}