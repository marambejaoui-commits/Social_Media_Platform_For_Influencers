package com.example.social_media_platform_for_influencers.servicesImplement;

import com.example.social_media_platform_for_influencers.entities.Post;
import com.example.social_media_platform_for_influencers.entities.User;
import com.example.social_media_platform_for_influencers.enums.RoleType;
import com.example.social_media_platform_for_influencers.repositories.CommentsRepo;
import com.example.social_media_platform_for_influencers.repositories.PostRepo;
import com.example.social_media_platform_for_influencers.repositories.ReactRepo;
import com.example.social_media_platform_for_influencers.repositories.UserRepo;
import com.example.social_media_platform_for_influencers.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserImplement implements UserInterface {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
@Autowired
private CommentsRepo commentRepo;
@Autowired
private ReactRepo reactRepo;
    @Override
    public User adduser(User user){
        if(user.getPassword().equals(user.getConfirmPassword())){
        return userRepo.save(user);}
        else throw new RuntimeException("password is invalid");
    }

    @Override
    public List<User> addallusers(List<User> users) {
        return userRepo.saveAll(users);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("❌ User not found with id: " + userId);
        }
        userRepo.deleteById(userId);
    }

    @Override
    public boolean verifPassword(Long userId, String rawPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ User not found with id: " + userId));
        // Vérification en clair

        return rawPassword.equals(user.getPassword());
    }

    @Override
    public boolean existById(Long userId) {
        return userRepo.existsById(userId);
    }

    @Override
    public long CountUsers() {
        return userRepo.count();
    }

    @Override
    public User updateUser(Long userId, User user) {
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ User not found with id: " + userId));
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setConfirmPassword(user.getConfirmPassword());
        u.setAdress(user.getAdress());
        u.setBio(user.getBio());
        u.setUsername(user.getUsername());
        return userRepo.save(u);
    }

    @Override
    public User getById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ User not found with id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Long countUsersByRole(RoleType roleType) {
        return userRepo.countUsersByRoleType(roleType);
    }

    @Override
    public List<User> getAllInfluencers() {
        return userRepo.findAllByRoleType(RoleType.Influencer);
    }

    public String changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("❌ User not found with id: " + userId));

        if (!oldPassword.equals(user.getPassword())) {
            return "❌ Old password incorrect";
        }

        user.setPassword(newPassword);
        user.setConfirmPassword(newPassword);// Mot de passe en clair
        userRepo.save(user);
        return "✅ Password updated successfully";
    }

    @Override
    public List<User> detectSuspiciousUsers() {
        List<User> users = userRepo.findAll();
        List<User> suspiciousUsers = new ArrayList<>();

        for (User user : users) {
            int score = 0;

            // 1️⃣ Vérifier email temporaire
            if (user.getEmail() != null &&
                    (user.getEmail().endsWith("@mailinator.com") || user.getEmail().endsWith("@tempmail.com"))) {
                score += 2;
            }

            // 2️⃣ Nombre de posts aujourd'hui
            Long postsToday = postRepo.countPostsToday(user.getUserId());
            if (postsToday > 50) {
                score += 2;
            } else if (postsToday > 20) { // seuil intermédiaire
                score += 1;
            }

            // 3️⃣ Vitesse de publication (posts très rapprochés)
            List<Post> todayPosts = postRepo.findPostsToday(user.getUserId());
            if (todayPosts.size() > 1) {
                todayPosts.sort(Comparator.comparing(Post::getCreatedAt));
                for (int i = 1; i < todayPosts.size(); i++) {
                    long diffMinutes = java.time.Duration.between(
                            todayPosts.get(i - 1).getCreatedAt(),
                            todayPosts.get(i).getCreatedAt()
                    ).toMinutes();
                    if (diffMinutes < 1) { // posts à moins d'1 minute
                        score += 1;
                        break; // pas besoin de compter plusieurs fois
                    }
                }
            }

            // 4️⃣ Multi-comptes sur la même IP
           Long accountsByIP = users.stream()
                    .filter(u -> u.getAdress() != null && u.getAdress().equals(user.getAdress()))
                    .count();
            if (accountsByIP > 3) score += 2;

            // 5️⃣ Analyse de contenu simple (mots spam / liens)
            List<Post> userPosts = postRepo.findAllByUser_UserId(user.getUserId());
            long spamCount = userPosts.stream()
                    .filter(p -> {
                        String content = p.getContent().toLowerCase();
                        return content.contains("spam") || content.contains("http");
                    })
                    .count();
            if (spamCount > 5) score += 2;  // plusieurs posts spam → suspect
            else if (spamCount > 0) score += 1; // un ou deux posts spam → léger soupçon

            // 6️⃣ Déterminer si l'utilisateur est suspect
            if (score >= 5) {
                user.setSuspicious(true);
                suspiciousUsers.add(user);
            } else {
                user.setSuspicious(false);
            }
        }

        // Sauvegarder seulement les utilisateurs marqués comme suspects
        userRepo.saveAll(suspiciousUsers);

        return suspiciousUsers;
    }



    @Override
    public List<User> getUserByEmail(String email) {
        return userRepo.findByEmailContaining(email);
    }

    @Override
    public  List<User> getUserByUsername(String username) {
        return userRepo.findByUsernameContaining(username);
    }

    @Override
    public User login(String email, String rawPassword) {
        // Récupération de l'utilisateur par email
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("❌ User not found with email: " + email));

        // Vérification du mot de passe
        if (!rawPassword.equals(user.getPassword())) {
            throw new RuntimeException("❌ Invalid password");
        }

        // Retourne l'utilisateur authentifié
        return user;
    }



}
