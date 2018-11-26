package com.amadeus.ist.springrest.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "members")
public class MemberDTO {
    @Id
    @JsonProperty("_id")
    public ObjectId _id;

    @JsonProperty("flyerID")
    private final String flyerID;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("lastname")
    private final String lastname;

    @JsonProperty("flyerPoints")
    private final int flyerPoints;

    @JsonProperty("status")
    private final String status;

    public MemberDTO(ObjectId _id, String flyerID, String name, String lastname, int flyerPoints, String status) {
        this._id = _id;
        this.flyerID = flyerID;
        this.name = name;
        this.lastname = lastname;
        this.flyerPoints = flyerPoints;
        this.status = status;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getFlyerID() {
        return flyerID;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public int getFlyerPoints() {
        return flyerPoints;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MemberDTO memberDto = (MemberDTO) o;
        return Objects.equals(this._id, memberDto._id) && Objects.equals(this.name, memberDto.name)
                && Objects.equals(this.flyerID, memberDto.flyerID) && Objects.equals(this.lastname, memberDto.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, flyerID, name, lastname);
    }

    public Member toConvertMember() {
        Member member = Member.MemberBuilder.newInstance()
                .setFlyerID(this.flyerID)
                .setName(this.name)
                .setLastname(this.lastname)
                .setFlyerPoints(this.flyerPoints)
                .setStatus(this.status).build();
        return member;
    }
}
