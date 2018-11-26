package com.amadeus.ist.springrest.member;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "members")
@JsonDeserialize(builder = Member.MemberBuilder.class)
public final class Member {

    @Id
    @NotNull
    private final String flyerID;
    @NotNull
    private final String name;
    @NotNull
    private final String lastname;
    private final int flyerPoints;
    private final String status;

    public Member(MemberBuilder builder) {
        this.flyerID = builder.flyerID;
        this.name = builder.name;
        this.lastname = builder.lastname;
        this.flyerPoints = builder.flyerPoints;
        this.status = builder.status;
    }

    public String getFlyerID() {
        return flyerID;
    }

    public int getFlyerPoints() {
        return flyerPoints;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        if (this.getClass() != o.getClass())
            return false;
        Member m = (Member) o;
        return this.flyerID.equals(m.getFlyerID());
    }

    @Override
    public int hashCode() {
        // prime numbers are used to ensure uniqueness of the hashcode
        int hash = 11;
        hash = 31 * hash + Integer.parseInt(flyerID);
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (lastname == null ? 0 : lastname.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "Member information: " + this.flyerID + " " + this.name + " " + this.lastname + " " + this.flyerPoints;
    }

    public MemberDTO toConvertMemberDTO() {
        MemberDTO memberDTO = new MemberDTO(new ObjectId(), this.flyerID, this.name, this.lastname, this.flyerPoints, this.status);
        return memberDTO;

    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static class MemberBuilder {
        private String flyerID;
        private String name;
        private String lastname;
        private int flyerPoints;
        private String status;

        // factory method used fo preventing this reference to be escaped.
        public static MemberBuilder newInstance() {
            return new MemberBuilder();
        }

        private MemberBuilder() {
        }

        public MemberBuilder setFlyerID(String flyerID) {
            if (flyerID.length() != 13)
                throw new IllegalArgumentException("FlyerID must contain 13 digits");
            this.flyerID = flyerID;
            return this;
        }

        public MemberBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MemberBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public MemberBuilder setFlyerPoints(int flyerPoints) {
            this.flyerPoints = flyerPoints;
            return this;
        }

        public MemberBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
