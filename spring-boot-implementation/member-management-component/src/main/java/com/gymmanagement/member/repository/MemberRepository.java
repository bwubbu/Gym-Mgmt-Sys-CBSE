// package com.gymmanagement.member.repository;

// import com.gymmanagement.base.entity.Member;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// /**
//  * Repository for Member Entity
//  * Extends JpaRepository to provide CRUD operations
//  */
// @Repository
// public interface MemberRepository extends JpaRepository<Member, Integer> {
//     // We use Integer because regId in Member/Person is an int
// }

package com.gymmanagement.member.repository;

import com.gymmanagement.base.entity.Member;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.*;

@Repository
public class MemberRepository {
    private final String FILE_NAME = "members.dat";

    public Member save(Member member) {
        List<Member> members = findAll();
        // Remove existing record if it's an update, then add
        members.removeIf(m -> m.getRegId() == member.getRegId());
        members.add(member);
        saveToFile(members);
        return member;
    }

    // @SuppressWarnings("unchecked")
    // public List<Member> findAll() {
    //     File file = new File(FILE_NAME);
    //     if (!file.exists()) return new ArrayList<>();
        
    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
    //         return (List<Member>) ois.readObject();
    //     } catch (IOException | ClassNotFoundException e) {
    //         return new ArrayList<Member>();
    //     }
    // }

    @SuppressWarnings("unchecked")
    public List<Member> findAll() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<Member>(); // Explicit type
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Member>) ois.readObject(); // Manual cast here is REQUIRED
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<Member>();
        }
    }

    public Optional<Member> findById(int id) {
        return findAll().stream().filter(m -> m.getRegId() == id).findFirst();
    }

    public void deleteById(int id) {
        List<Member> members = findAll();
        members.removeIf(m -> m.getRegId() == id);
        saveToFile(members);
    }

    private void saveToFile(List<Member> members) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(members);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}