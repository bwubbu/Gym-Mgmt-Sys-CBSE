// package com.gymmanagement.member.repository;

// import com.gymmanagement.base.entity.MemberPlan;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface MemberPlanRepository extends JpaRepository<MemberPlan, String> {
//     // String because PlanID is a String (e.g., "PLAN-001")
// }

package com.gymmanagement.member.repository;

import com.gymmanagement.base.entity.MemberPlan;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.*;

@Repository
public class MemberPlanRepository {
    private final String FILE_NAME = "member_plans.dat";

    public MemberPlan save(MemberPlan plan) {
        List<MemberPlan> plans = findAll();
        plans.removeIf(p -> p.getPlanId().equals(plan.getPlanId()));
        plans.add(plan);
        saveToFile(plans);
        return plan;
    }

    // @SuppressWarnings("unchecked")
    // public List<MemberPlan> findAll() {
    //     File file = new File(FILE_NAME);
    //     if (!file.exists()) return new ArrayList<>();

    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
    //         return (List<MemberPlan>) ois.readObject();
    //     } catch (IOException | ClassNotFoundException e) {
    //         return new ArrayList<>();
    //     }
    // }
    @SuppressWarnings("unchecked")
    public List<MemberPlan> findAll() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<MemberPlan>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<MemberPlan>) ois.readObject(); // Manual cast here is REQUIRED
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<MemberPlan>();
        }
    }

    public Optional<MemberPlan> findById(String id) {
        return findAll().stream().filter(p -> p.getPlanId().equals(id)).findFirst();
    }

    public void deleteById(String id) {
        List<MemberPlan> plans = findAll();
        // Remove the plan with the matching ID
        boolean removed = plans.removeIf(p -> p.getPlanId().equals(id));
        
        // If something was removed, update the file
        if (removed) {
            saveToFile(plans);
        }
    }

    private void saveToFile(List<MemberPlan> plans) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(plans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}