//package org.example.project.security.user;
//
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//import static org.apache.logging.log4j.util.Strings.concat;
//
//@Service
//
//public class RoleServiceImpl implements RoleService {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Transactional
//    @Override
//    public Role addRole(String email,String password) {
//        String concat = concat(email, password);
//        return roleRepository.save(new Role(concat));
//
//    }
//
//    @Transactional
//    @Override
//    public Role getRole(String email,String password) {
//        String concat = concat(email, password);
//        Role byName = roleRepository.findByName(concat);
//        if (byName == null) {
//            return null;
//        } else{
//            return byName;
//        }
//    }
//
//    @Transactional
//    @Override
//    public void removeRole(String email,String password) {
//        String concat = concat(email, password);
//        Role byName = roleRepository.findByName(concat);
//        if (byName != null) {
//            roleRepository.delete(byName);
//        } else{
//            System.out.println("Role not found");
//        }
//    }
//}
